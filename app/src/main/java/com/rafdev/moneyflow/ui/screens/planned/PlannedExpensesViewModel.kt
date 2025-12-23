package com.rafdev.moneyflow.ui.screens.planned

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.moneyflow.utils.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannedExpensesViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val numberFormatter: NumberFormatter
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseState())
    val state: StateFlow<ExpenseState> = _state

    init {
        fetchExpenses()
    }

    fun deleteExpenseById(id:Int){
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id)
        }
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
           getExpenseUseCase.invoke().collect{expenses ->
               val fixedExpenses = expenses.filter { it.type == "fixed" }
               _state.value = ExpenseState(success = fixedExpenses)
           }
        }
    }




}

data class ExpenseState(
    val isLoading: Boolean = false,
    val success: List<Expense>? = null,
    val error: String = ""
)