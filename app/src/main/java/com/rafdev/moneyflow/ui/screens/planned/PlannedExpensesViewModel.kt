package com.rafdev.moneyflow.ui.screens.planned

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlannedExpensesViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseState())
    val state: StateFlow<ExpenseState> = _state

    init {
        fetchExpenses()
    }



    fun saveExpense(title: String, description: String, amount: Double) {
        val currentDateTime = getCurrentDateTime()

        Log.e("pruebas", "ss $currentDateTime")
        val expense = Expense(
            id = 0,
            name = title,
            amount = amount,
            type = "",
            description = description,
            image = "",
            color = "",
            date = currentDateTime,
            category = "",
            recurring = false,
            period = "",
            paymentMethod = "",
            notes = "",
            isPaid = false
        )

        viewModelScope.launch {
            insertExpenseUseCase.invoke(expense)
        }
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
           getExpenseUseCase.invoke().collect{
               _state.value = ExpenseState(success = it)
           }
        }
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date()) // Genera la fecha actual
    }
}

data class ExpenseState(
    val isLoading: Boolean = false,
    val success: List<Expense>? = null,
    val error: String = ""
)