package com.rafdev.moneyflow.ui.screens.monthly_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.salary.GetSalariesUseCase
import com.rafdev.moneyflow.utils.isSameMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlannedExpensesViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getSalariesUseCase: GetSalariesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseState())
    val state: StateFlow<ExpenseState> = _state

    private val _salary = MutableStateFlow("Ingresa tu sueldo")
    val salary: StateFlow<String> = _salary


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
    }

    private fun observeMonth() {
        viewModelScope.launch {
            currentMonth.collect { month ->
                fetchExpenses(month.month, month.year)
                getSalaries(month.month, month.year)
            }
        }
    }

    fun deleteExpenseById(id: Int, creditCardId: Int?) {
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id, creditCardId)
        }
    }

    private fun fetchExpenses(month: Int, year: Int) {
        viewModelScope.launch {
            getExpenseUseCase()
                .onStart {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }
                .collect { result ->
                    result.onSuccess { expenses ->
                        val filtered = expenses.filter {
                            isSameMonth(it.date, month, year)
                        }

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

    private fun getSalaries(month: Int, year: Int) {
        viewModelScope.launch {
            getSalariesUseCase.invoke().collect { result ->
                result.onSuccess { salary ->
                    val filtered = salary.filter {
                        isSameMonth(it.date, month, year)
                    }

                    val total = filtered.sumOf { it.amount }

                    _salary.update {
                        if (total == 0.0) {
                            "Agregar saldo"
                        } else {
                            total.toString()
                        }
                    }
                }

                result.onFailure { error ->
                    _salary.update { "error" }
                }
            }
        }
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.next()
    }

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.previous()
    }

}

data class ExpenseState(
    val isLoading: Boolean = false,
    val success: List<Expense> = emptyList(),
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

