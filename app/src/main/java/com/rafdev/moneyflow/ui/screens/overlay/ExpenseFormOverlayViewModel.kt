package com.rafdev.moneyflow.ui.screens.overlay

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.usecase.creditCard.GetAllCreditCardUC
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
import javax.inject.Inject

@HiltViewModel
class ExpenseFormOverlayViewModel @Inject constructor(
    private val getAllCreditCardUC: GetAllCreditCardUC
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var paymentMethod by mutableStateOf("CASH")

    var selectedCreditCardId by mutableStateOf<Int?>(null)
        private set

    init {
        getAllCreditCards()
    }

    fun onCreditCardSelected(cardId: Int) {
        selectedCreditCardId = cardId
    }

    fun onPaymentMethodChange(method: String) {
        paymentMethod = method

        if (method != "CREDIT") {
            selectedCreditCardId = null
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

    fun saveForm(){
        Log.d("viewmodelForm", "id $selectedCreditCardId")
    }

}

data class UiState(
    val loading: Boolean = false,
    val data: List<CreditCardDomain> = emptyList(),
    val error: String = ""
)