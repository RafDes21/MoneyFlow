package com.rafdev.moneyflow.ui.screens.form.expense.fix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.expense.GetExpenseByIdUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.model.ExpenseFormUi
import com.rafdev.moneyflow.utils.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class FormExpenseFixViewModel @Inject constructor(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(FormExpenseUiState())
    val uiState: StateFlow<FormExpenseUiState> = _uiState

    private val _event = MutableSharedFlow<FormExpenseEvent>()
    val event = _event.asSharedFlow()


    fun initForm(expenseId: Int?) {
        if (expenseId == null) {
            resetForm()
            return
        }

        viewModelScope.launch {
            getExpenseByIdUseCase(expenseId)
                .onSuccess { expense ->
                    _uiState.update {
                        it.copy(
                            id = expense.id,
                            title = expense.name,
                            description = expense.description,
                            amount = expense.amount.toString(),
                            date = expense.date,
                            isValid = true
                        )
                    }
                }
                .onFailure {
                    _event.emit(FormExpenseEvent.Error("Expense no encontrado"))
                }
        }
    }

    private fun validate(title: String, amount: String): Boolean {
        return title.isNotBlank() &&
                amount.isNotBlank() &&
                amount.toDoubleOrNull() != null
    }

    fun onTitleChange(value: String) {
        _uiState.update {
            it.copy(
                title = value,
                isValid = validate(value, it.amount)
            )
        }
    }

    fun onAmountChange(value: String) {
        _uiState.update {
            it.copy(
                amount = value,
                isValid = validate(it.title, value)
            )
        }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update { it.copy(description = value) }
    }


    fun saveExpense() {
        val state = _uiState.value

        val finalDate = state.date.ifBlank {
            getCurrentDateTime()
        }

        val expense = Expense(
            id = state.id ?: 0,
            name = state.title,
            amount = state.amount.toDouble(),
            type = "fixed",
            description = state.description,
            image = "",
            color = "",
            date = finalDate,
            category = "",
            recurring = false,
            period = "",
            paymentMethod = "",
            notes = "",
            isPaid = false,
            creditCardId = null
        )


        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            insertExpenseUseCase(expense)
                .onSuccess {
                    _event.emit(FormExpenseEvent.Success)
                }
                .onFailure {
                    _event.emit(
                        FormExpenseEvent.Error("Error al guardar")
                    )
                }


            _uiState.update { it.copy(isLoading = false) }
        }
    }


    fun resetForm() {
        _uiState.value = FormExpenseUiState()
    }

}

data class FormExpenseUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val amount: String = "",
    val date: String = "",
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class FormExpenseEvent {
    object Success : FormExpenseEvent()
    data class Error(val message: String) : FormExpenseEvent()
}