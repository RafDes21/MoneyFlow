package com.rafdev.moneyflow.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Budget
import com.rafdev.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.rafdev.domain.usecase.budget.GetBudgetUseCase
import com.rafdev.domain.usecase.budget.SaveBudgetUseCase
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.moneyflow.ui.screens.planned.ExpenseState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val saveBudgetUseCase: SaveBudgetUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _budget = MutableStateFlow(0.0)
    val budget: StateFlow<Double> = _budget

    private val _fixedExpensesAmount = MutableStateFlow(0.0)
    val fixedExpensesAmount: StateFlow<Double> = _fixedExpensesAmount

    private val _remainingBudget = MutableStateFlow(0.0)
    val remainingBudget: StateFlow<Double> = _remainingBudget

    private val _state = MutableStateFlow(ExpenseStateHome())
    val state: StateFlow<ExpenseStateHome> = _state

    init {
        getBudget()
        getExpenses()
        fetchExpenses()
    }

    private fun getExpenses(){
        viewModelScope.launch {
            getExpenseUseCase.invoke().collect{expenses ->
                val totalFixedExpenses = expenses.sumOf { it.amount }
                _fixedExpensesAmount.value = totalFixedExpenses
                updateRemainingBudget()
            }
        }
    }

    private fun getBudget() {
        viewModelScope.launch {
            getBudgetUseCase.invoke().collect { value ->
                _budget.value = value.totalBudget
                updateRemainingBudget()
            }
        }
    }

    fun updateBudget(newBudget: Budget) {
        viewModelScope.launch {
            saveBudgetUseCase.invoke(newBudget)
        }
    }
    private fun updateRemainingBudget() {
        val remaining = _budget.value - _fixedExpensesAmount.value
        _remainingBudget.value = remaining
    }

    fun saveExpense(title: String, description: String,currentDateTime:String, amount: Double) {

        val expense = Expense(
            id = 0,
            name = title,
            amount = amount,
            type = "recurring",
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

    fun deleteExpenseById(id:Int){
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id)
        }
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
            getExpenseUseCase.invoke().collect{expenses ->
                val fixedExpenses = expenses.filter { it.type == "recurring" }
                _state.value = ExpenseStateHome(success = fixedExpenses)
            }
        }
    }

}

data class ExpenseStateHome(
    val isLoading: Boolean = false,
    val success: List<Expense>? = null,
    val error: String = ""
)