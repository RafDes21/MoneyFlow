package com.rafdev.moneyflow.ui.screens.creditcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.usecase.creditCard.DeleteCreditCardUC
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getAllCreditCardUC: GetAllCreditCardUC,
    private val deleteCreditCardUC: DeleteCreditCardUC
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getAllCreditCard()
    }

    private fun getAllCreditCard() {
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

    fun deleteCreditCardById(id: Int) {
        viewModelScope.launch {
            deleteCreditCardUC.invoke(id)
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val data: List<CreditCardDomain> = emptyList(),
    val error: String = ""
)