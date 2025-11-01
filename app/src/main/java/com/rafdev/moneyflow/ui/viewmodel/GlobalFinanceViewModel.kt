package com.rafdev.moneyflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafdev.domain.model.Expense
import com.rafdev.domain.usecase.expense.InsertExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope


@HiltViewModel
class GlobalFinanceViewModel @Inject constructor(
    private val insertExpenseUseCase: InsertExpenseUseCase
) : ViewModel() {

    fun saveExpense(
        title: String,
        description: String,
        currentDateTime: String,
        amount: Double,
        typeValue: Int
    ) {
        val type = if (typeValue == 0) "recurring" else "fixed"

        val expense = Expense(
            id = 0,
            name = title,
            amount = amount,
            type = type,
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

}