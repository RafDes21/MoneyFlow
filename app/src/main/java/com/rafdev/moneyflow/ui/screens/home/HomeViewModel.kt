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
import com.rafdev.moneyflow.utils.NumberFormatter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val saveBudgetUseCase: SaveBudgetUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val numberFormatter: NumberFormatter
) : ViewModel() {


    private val _split = MutableStateFlow(SplitNumber())
    val split: StateFlow<SplitNumber> = _split

    private val _budget = MutableStateFlow("")
    val budget: StateFlow<String> = _budget

    private val _fixedExpensesAmount = MutableStateFlow("")
    val fixedExpensesAmount: StateFlow<String> = _fixedExpensesAmount

    private val _expensesRecurrent = MutableStateFlow("")
    val expensesRecurrent: StateFlow<String> = _expensesRecurrent

    private val _numericBudget = MutableStateFlow(0.0)
    private val numericBudget: StateFlow<Double> = _numericBudget

    private val _numericFixedExpenses = MutableStateFlow(0.0)
    private val numericFixedExpenses: StateFlow<Double> = _numericFixedExpenses

    private val _numericRecurrentExpenses = MutableStateFlow(0.0)
    private val numericRecurrentExpenses: StateFlow<Double> = _numericRecurrentExpenses

    private val _numericRemainingBudget = MutableStateFlow("")
    val numericRemainingBudget: StateFlow<String> = _numericRemainingBudget

    private val _state = MutableStateFlow(ExpenseStateHome())
    val state: StateFlow<ExpenseStateHome> = _state

    init {
        getBudget()
        getExpenses()
        fetchExpenses()
        observeValues()
    }

    private fun getExpenses() {
        viewModelScope.launch {
            getExpenseUseCase.invoke().collect { expenses ->
                val fixedExpenses = expenses.filter { it.type == "fixed" }
                val totalFixedExpenses = fixedExpenses.sumOf { it.amount }
                _numericFixedExpenses.value = totalFixedExpenses
                val result = numberFormatter.formatToString(totalFixedExpenses)
                _fixedExpensesAmount.value = result
            }
        }
    }

    private fun getBudget() {
        viewModelScope.launch {
            getBudgetUseCase.invoke().collect { value ->
                _numericBudget.value = value.totalBudget
                val formattedBudget = numberFormatter.formatToString(value.totalBudget)
                val splitResult = numberFormatter.splitNumBer(formattedBudget)
                _split.value = splitResult
                _budget.value = formattedBudget
            }
        }
    }

    private fun updateBudget(newBudget: Budget) {
        viewModelScope.launch {
            saveBudgetUseCase.invoke(newBudget)
        }
    }

    private fun observeValues() {
        viewModelScope.launch {
            combine(numericBudget, numericFixedExpenses, numericRecurrentExpenses) { budget, fixed, recurrent ->
                budget - fixed - recurrent
            }.collect { newRemainingBudget ->
                _numericRemainingBudget.value = numberFormatter.formatToString(newRemainingBudget)
            }
        }
    }

    fun saveExpense(title: String, description: String, currentDateTime: String, amount: Double) {

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

    fun deleteExpenseById(id: Int) {
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id)
        }
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
            getExpenseUseCase.invoke().collect { expenses ->
                val expensesRecurring = expenses.filter { it.type == "recurring" }
                _state.value = ExpenseStateHome(success = expensesRecurring)
                val totalFixedExpenses = expensesRecurring.sumOf { it.amount }
                _numericRecurrentExpenses.value = totalFixedExpenses
                val result = numberFormatter.formatToString(totalFixedExpenses)
                _expensesRecurrent.value = result
            }
        }
    }


    fun handleNumberInput(input: String): Boolean {
        val result = numberFormatter.parseAndFormatToDouble(input)
        result?.let {
            val new = Budget(
                totalBudget = it
            )
            updateBudget(new)
            return true
        } ?: run {
            return false
        }
    }

}

data class ExpenseStateHome(
    val isLoading: Boolean = false,
    val success: List<Expense>? = null,
    val error: String = ""
)

data class SplitNumber(
    val separator: String = "",
    val integerPart: String = "",
    val decimalPart: String = ""
)