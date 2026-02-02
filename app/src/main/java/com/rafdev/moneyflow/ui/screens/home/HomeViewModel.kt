package com.rafdev.moneyflow.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.domain.usecase.salary.GetSalariesUseCase
import com.rafdev.moneyflow.utils.NumberFormatter
import com.rafdev.moneyflow.utils.isSameMonth
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val numberFormatter: NumberFormatter,
    private val getSalariesUseCase: GetSalariesUseCase

) : ViewModel() {


    private val _splitRecurrent = MutableStateFlow(SplitNumber())
    val splitRecurrent: StateFlow<SplitNumber> = _splitRecurrent

    private val _splitFixed = MutableStateFlow(SplitNumber())
    val splitFixed: StateFlow<SplitNumber> = _splitFixed


    private val _total = MutableStateFlow(SplitNumber())
    val total: StateFlow<SplitNumber> = _total

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> = _totalExpenses

    private val _totalSalary = MutableStateFlow(0.0)
    val totalSalary: StateFlow<Double> = _totalSalary


    private val _state = MutableStateFlow(ExpenseStateHome())
    val state: StateFlow<ExpenseStateHome> = _state

    private val _salary = MutableStateFlow("Ingresa tu sueldo")
    val salary: StateFlow<String> = _salary

    private val _salaryNotLoaded = MutableStateFlow(true)
    val salaryNotLoaded: StateFlow<Boolean> = _salaryNotLoaded


    init {
        fetchExpenses()
        getCurrentMonthSalary()
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
            isPaid = false,
            creditCardId = null
        )

        viewModelScope.launch {
            insertExpenseUseCase.invoke(expense)
        }
    }

    fun deleteExpenseById(id: Int, creditCardId: Int?) {
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id, creditCardId)
        }
    }

    private fun fetchExpenses() {

        val now = LocalDate.now()

        viewModelScope.launch {
            getExpenseUseCase().collect { result ->
                result.onSuccess { expenses ->
                    val filtered = expenses.filter {
                        isSameMonth(it.date, now.monthValue, now.year)
                    }
                    val total = filtered.sumOf { it.amount }
                    _totalExpenses.update { total }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            success = filtered
                        )
                    }
                }

                result.onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error"
                        )
                    }
                }
            }
        }
    }


    private fun getCurrentMonthSalary() {
        val now = LocalDate.now()

        viewModelScope.launch {
            getSalariesUseCase.invoke().collect { result ->
                result.onSuccess { salaries ->

                    val filtered = salaries.filter {
                        isSameMonth(it.date, now.monthValue, now.year)
                    }

                    val total = filtered.sumOf { it.amount }
                    _totalSalary.update { total }
                    _salary.update {
                        if (total == 0.0) {
                            "Agregar saldo"
                        } else {
                            total.toString()
                        }
                    }
                    _salaryNotLoaded.update {
                        if (total == 0.0) {
                            true
                        } else {
                            false
                        }
                    }

                }

                result.onFailure {
                    _salary.update { "Error" }
                }
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