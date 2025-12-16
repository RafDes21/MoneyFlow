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
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val numberFormatter: NumberFormatter
) : ViewModel() {


    private val _splitRecurrent = MutableStateFlow(SplitNumber())
    val splitRecurrent: StateFlow<SplitNumber> = _splitRecurrent

    private val _splitFixed = MutableStateFlow(SplitNumber())
    val splitFixed: StateFlow<SplitNumber> = _splitFixed


    private val _total = MutableStateFlow(SplitNumber())
    val total: StateFlow<SplitNumber> = _total

    private val _totalExpenses = MutableStateFlow("")
    val totalExpenses: StateFlow<String> = _totalExpenses

    private val _totalFixedExpenses = MutableStateFlow("")
    val totalFixedExpenses: StateFlow<String> = _totalFixedExpenses

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
        fetchExpenses()
        observeValues()
    }

    private fun observeValues() {
        viewModelScope.launch {
            combine(numericFixedExpenses, numericRecurrentExpenses) { fixed, recurrent ->
                fixed + recurrent
            }.collect { newRemainingBudget ->
                val formattedBudget = numberFormatter.formatToString(newRemainingBudget)
                val splitResult = numberFormatter.splitNumBer(formattedBudget)
                _total.value = splitResult
                _totalExpenses.value = newRemainingBudget.toString()
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
                _state.value = ExpenseStateHome(success = expenses)

                val expensesRecurring = expenses.filter { it.type == "recurring" }
                val totalRecurrentExpenses = expensesRecurring.sumOf { it.amount }
                _numericRecurrentExpenses.value = totalRecurrentExpenses

                val formattedBudget = numberFormatter.formatToString(totalRecurrentExpenses)
                val splitResult = numberFormatter.splitNumBer(formattedBudget)
                _splitRecurrent.value = splitResult


                val expensesFixed = expenses.filter { it.type == "fixed" }
                val totalFixedExpenses = expensesFixed.sumOf { it.amount }
                _numericFixedExpenses.value = totalFixedExpenses

                val formattedFixed = numberFormatter.formatToString(totalFixedExpenses)
                val splitFixedResult = numberFormatter.splitNumBer(formattedFixed)
                _splitFixed.value = splitFixedResult
                _totalFixedExpenses.value = totalFixedExpenses.toString()


            }
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