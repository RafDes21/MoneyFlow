package com.rafdev.moneyflow.ui.screens.overlay

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.creditCard.GetAllCreditCardUC
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.moneyflow.utils.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseFormOverlayViewModel @Inject constructor(
    private val getAllCreditCardUC: GetAllCreditCardUC,
    private val insertExpenseUseCase: InsertExpenseUseCase
) : ViewModel() {

    private val _formState = MutableStateFlow(StateForm())
    val formState: StateFlow<StateForm> = _formState.asStateFlow()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var hasTriedToSave by mutableStateOf(false)
        private set
    val isSaveEnabled: Boolean
        get() = title.isNotBlank() && amount.isNotBlank()

    val showCreditCardError: Boolean
        get() = hasTriedToSave &&
                paymentMethod == "CREDIT" &&
                selectedCreditCardId == null



    var id by mutableIntStateOf(0)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var amount by mutableStateOf("")
        private set


    var paymentMethod by mutableStateOf("CASH")

    var selectedCreditCardId by mutableStateOf<Int?>(null)
        private set

    init {
        getAllCreditCards()
    }

    fun onCreditCardSelected(cardId: Int) {
        selectedCreditCardId = cardId
    }

    fun onIdChange(value: Int) {
        id = value
    }

    fun onTitleChange(value: String) {
        title = value
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun onAmountChange(value: String) {
        amount = value
    }

    fun onPaymentMethodChange(method: String) {
        paymentMethod = method

        if (method != "CREDIT") {
            selectedCreditCardId = null
            hasTriedToSave = false
        }
    }

    fun getAllCreditCards() {
        getAllCreditCardUC()
            .onStart {
                _uiState.value = _uiState.value.copy(loading = true)
            }
            .map { result ->
                result
                    .onSuccess { cards ->
                        _uiState.value = UiState(
                            loading = false,
                            data = cards,
                            error = ""
                        )
                    }
                    .onFailure { throwable ->
                        _uiState.value = UiState(
                            loading = false,
                            data = emptyList(),
                            error = throwable.message ?: "Error desconocido"
                        )
                    }
            }
            .catch { e ->
                _uiState.value = UiState(
                    loading = false,
                    data = emptyList(),
                    error = e.message ?: "Error inesperado"
                )
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun saveForm() {
        hasTriedToSave = true

        if (showCreditCardError) {
            return
        }

        _formState.value = StateForm(isLoading = true)

        val expense = Expense(
            id = id,
            name = title,
            amount = amount.toDouble(),
            type = "recurring",
            description = description,
            image = "",
            color = "",
            date = getCurrentDateTime(),
            category = "",
            recurring = false,
            period = "",
            paymentMethod = paymentMethod,
            notes = "",
            isPaid = false,
            creditCardId = selectedCreditCardId
        )

        viewModelScope.launch {
            _formState.value = StateForm(isLoading = true)

            runCatching {
                insertExpenseUseCase(expense)
            }.onSuccess {
                _formState.value = StateForm(
                    isLoading = true,
                    success = "success"
                )
            }.onFailure {
                _formState.value = StateForm(
                    isLoading = false,
                    error = "error"
                )
            }
        }
    }

    fun onReset() {
        id = 0
        title = ""
        description = ""
        amount = ""
        paymentMethod = "CASH"
        selectedCreditCardId = null

        hasTriedToSave = false

        _formState.value = StateForm()
    }

}

data class UiState(
    val loading: Boolean = false,
    val data: List<CreditCardDomain> = emptyList(),
    val error: String = ""
)

data class StateForm(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = ""
)