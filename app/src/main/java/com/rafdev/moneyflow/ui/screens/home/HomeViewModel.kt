package com.rafdev.moneyflow.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.model.Expense
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.usecase.creditCard.DeleteCreditCardUC
import com.rafdev.domain.usecase.creditCard.GetAllCreditCardUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.rafdev.domain.usecase.expense.DeleteExpenseUseCase
import com.rafdev.domain.usecase.expense.GetExpenseUseCase
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import com.rafdev.domain.usecase.salary.AddSalaryUseCase
import com.rafdev.domain.usecase.salary.GetSalariesUseCase
import com.rafdev.moneyflow.utils.NumberFormatter
import com.rafdev.moneyflow.utils.getCurrentDateTime
import com.rafdev.moneyflow.utils.isSameMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    private val getSalariesUseCase: GetSalariesUseCase,
    private val getAllCreditCardUC: GetAllCreditCardUC,
    private val deleteCreditCardUC: DeleteCreditCardUC,
    private val insertSalaryUseCase: AddSalaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> = _totalExpenses

    private val _totalSalary = MutableStateFlow(0.0)
    val totalSalary: StateFlow<Double> = _totalSalary


    private val _state = MutableStateFlow(ExpenseStateHome())
    val state: StateFlow<ExpenseStateHome> = _state

    private val _salary = MutableStateFlow<Salary?>(null)
    val salary: StateFlow<Salary?> = _salary


    init {
        fetchExpenses()
        getCurrentMonthSalary()
        getAllCreditCard()
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

                    _salary.update { filtered.firstOrNull() }
                }

                result.onFailure {
                    _salary.update { null }

                }
            }
        }
    }


    private fun getAllCreditCard() {
        getAllCreditCardUC()
            .onStart {
                _uiState.value = _uiState.value.copy(loading = true)
            }
            .map { result ->
                result
                    .onSuccess { cards ->
                        _uiState.value = UiState(
                            loading = false,
                            data = cards,
                            error = ""
                        )
                    }
                    .onFailure { throwable ->
                        _uiState.value = UiState(
                            loading = false,
                            data = emptyList(),
                            error = throwable.message ?: "Error desconocido"
                        )
                    }
            }
            .catch { e ->
                _uiState.value = UiState(
                    loading = false,
                    data = emptyList(),
                    error = e.message ?: "Error inesperado"
                )
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun deleteCreditCardById(id: Int) {
        viewModelScope.launch {
            deleteCreditCardUC.invoke(id)
        }

    }


    fun toggleHideSalary() {
        val current = _salary.value ?: return
        viewModelScope.launch {
            insertSalaryUseCase(
                CreateSalary(
                    id = current.id,
                    companyName = current.companyName,
                    amount = current.amount,
                    date = current.date,
                    isHidden = !current.isHidden
                )
            )
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


data class UiState(
    val loading: Boolean = false,
    val data: List<CreditCardDomain> = emptyList(),
    val error: String = ""
)