package com.rafdev.domain.repository

import com.rafdev.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface RepositoryExpense {

    suspend fun getExpenseById(id: Int): Result<Expense>
    fun getExpense(): Flow<Result<List<Expense>>>
    suspend fun insertExpense(expense: Expense): Result<Unit>
    suspend fun deleteExpenseById(expenseId: Int, creditCardId: Int?)
}