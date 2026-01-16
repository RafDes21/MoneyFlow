package com.rafdev.moneyflow.ui.screens.planned

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.moneyflow.utils.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
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

    private val _currentMonth =
        MutableStateFlow(
            UiMonth(
                year = LocalDate.now().year,
                month = LocalDate.now().monthValue
            )
        )

    val currentMonth: StateFlow<UiMonth> = _currentMonth


    init {
        observeMonth()
        fetchExpenses()
    }
    private fun observeMonth() {
        viewModelScope.launch {
            currentMonth.collect { month ->
                Log.d(
                    "PlannedExpensesVM",
                    "Mes actual: ${month.monthName} (${month.month}) - Año: ${month.year}"
                )
            }
        }
    }
    fun deleteExpenseById(id: Int, creditCardId: Int?) {
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id, creditCardId)
        }
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
            getExpenseUseCase.invoke().collect { expenses ->
                val fixedExpenses = expenses.filter { it.type == "fixed" }
                _state.value = ExpenseState(success = fixedExpenses)
            }
        }
    }


    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.next()
    }

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.previous()
    }

    private fun currentMonthDate(): String {
        val month = _currentMonth.value
        return LocalDate.of(
            month.year,
            month.month,
            1
        ).toString()
    }
}

data class ExpenseState(
    val isLoading: Boolean = false,
    val success: List<Expense>? = null,
    val error: String = ""
)

data class UiMonth(
    val year: Int,
    val month: Int
) {
    fun next(): UiMonth {
        return if (month == 12) {
            copy(year = year + 1, month = 1)
        } else {
            copy(month = month + 1)
        }
    }

    fun previous(): UiMonth {
        return if (month == 1) {
            copy(year = year - 1, month = 12)
        } else {
            copy(month = month - 1)
        }
    }

    val monthName: String
        get() = Month.of(month)
            .getDisplayName(
                TextStyle.FULL,
                Locale("es", "ES")
            )
            .replaceFirstChar { it.uppercase() }
}

data class MonthlyUiState(
    val month: UiMonth,
    val salary: Salary? = null,
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = false
)

