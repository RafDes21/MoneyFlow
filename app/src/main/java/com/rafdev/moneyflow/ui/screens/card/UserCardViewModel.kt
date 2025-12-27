package com.rafdev.moneyflow.ui.screens.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.usecase.creditCard.InsertCreditCardUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCardViewModel @Inject constructor(
    private val insertCreditCardUC: InsertCreditCardUC
) : ViewModel() {

    private val _id = MutableStateFlow(0)
    val id: StateFlow<Int> = _id.asStateFlow()
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> = _number.asStateFlow()

    private val _cardType = MutableStateFlow(0)
    val cardType: StateFlow<Int> = _cardType.asStateFlow()

    private val _colorId = MutableStateFlow(1)
    val colorId: StateFlow<Int> = _colorId.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onNumberChange(newNumber: String) {
        _number.value = newNumber
    }

    fun onCardTypeChange(newType: Int) {
        _cardType.value = newType
    }

    fun onColorIdChange(newColor: Int) {
        _colorId.value = newColor
    }


    private val _formState = MutableStateFlow(CreditCardFormState())
    val formState: StateFlow<CreditCardFormState> = _formState.asStateFlow()

    fun createCreditCard() {
        val request = CreditCardDomain(
            id = _id.value,
            title = _title.value,
            number = _number.value,
            type = _cardType.value,
            color = _colorId.value,
            total = 0.0
        )

        viewModelScope.launch {
            _formState.value = _formState.value.copy(
                isLoading = true
            )

            runCatching {
                insertCreditCardUC(request)
            }.onSuccess {
                _formState.value = _formState.value.copy(
                    success = "Tarjeta creada correctamente"
                )
            }.onFailure {
                _formState.value = _formState.value.copy(
                    error = "Error al crear tarjeta"
                )
            }

        }

    }

    fun reset() {
        _formState.value = CreditCardFormState()
        _title.value = ""
        _number.value = ""
    }
}

data class CreditCardFormState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = ""
)