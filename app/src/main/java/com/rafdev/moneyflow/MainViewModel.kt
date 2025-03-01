package com.rafdev.moneyflow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rafdev.domain.usecase.budget.GetBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafdev.domain.model.Budget
import com.rafdev.domain.usecase.budget.SaveBudgetUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val saveBudgetUseCase: SaveBudgetUseCase
) : ViewModel() {

    private val _budget = MutableStateFlow(0.0)
    val budget: StateFlow<Double> = _budget

    init {
        getBudget()
    }

    private fun getBudget() {
        viewModelScope.launch {
            getBudgetUseCase.invoke().collect { value ->
                _budget.value = value.totalBudget
            }
        }
    }

    fun updateBudget(newBudget: Budget){
        viewModelScope.launch {
            saveBudgetUseCase.invoke(newBudget)
        }
    }

}