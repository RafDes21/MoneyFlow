package com.rafdev.moneyflow.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Budget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.rafdev.domain.usecase.budget.GetBudgetUseCase
import com.rafdev.domain.usecase.budget.SaveBudgetUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val saveBudgetUseCase: SaveBudgetUseCase
):ViewModel() {

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