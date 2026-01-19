package com.rafdev.moneyflow.ui.screens.form.salary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.usecase.salary.AddSalaryUseCase
import com.rafdev.domain.usecase.salary.UpdateSalaryUseCase
import com.rafdev.moneyflow.ui.screens.form.salary.model.SalarySheetMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val insertSalaryUseCase: AddSalaryUseCase,
    private val updateSalaryUseCase: UpdateSalaryUseCase,
    //private val getSalaryByIdUseCase: GetSalaryByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SalaryUiState())
    val uiState: StateFlow<SalaryUiState> = _uiState

    private val _event = MutableSharedFlow<Salary>()
    val event: SharedFlow<Salary> = _event

    private var currentSalaryId: Int? = null
    private var currentMode: SalarySheetMode = SalarySheetMode.CREATE

    fun init(mode: SalarySheetMode, salaryId: Int?) {
        currentMode = mode
        currentSalaryId = salaryId

        if (mode == SalarySheetMode.UPDATE && salaryId != null) {
            loadSalary(salaryId)
        }
    }

    private fun loadSalary(id: Int) {
      //  viewModelScope.launch {
         //   getSalaryByIdUseCase(id)?.let { salary ->
           //     _uiState.value = _uiState.value.copy(
             //       companyName = salary.companyName,
               //     amount = salary.amount.toString(),
                 //   isValid = true
                //)
            //}
        //}
    }

    fun onCompanyChange(value: String) {
        _uiState.update {
            val newState = it.copy(companyName = value)
            newState.copy(isValid = validate(newState))
        }
    }

    fun onAmountChange(value: String) {
        _uiState.update {
            val newState = it.copy(amount = value)
            newState.copy(isValid = validate(newState))
        }
    }

    private fun validate(state: SalaryUiState): Boolean {
        return state.companyName.isNotBlank() &&
                state.amount.toDoubleOrNull()?.let { it > 0 } == true
    }

    fun createSalary() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val now = LocalDate.now()

            val result = insertSalaryUseCase(
                CreateSalary(
                    companyName = _uiState.value.companyName,
                    amount = _uiState.value.amount.toDouble(),
                    year = now.year,
                    month = now.monthValue,
                    createdAt = System.currentTimeMillis()
                )
            )

            _uiState.update { it.copy(isLoading = false) }

            result.fold(
                onSuccess = {
                },
                onFailure = { error ->

                }
            )

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun updateSalary() {
        val id = currentSalaryId ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}


data class SalaryUiState(
    val companyName: String = "",
    val amount: String = "",
    val isValid: Boolean = false,
    val isLoading: Boolean = false
)