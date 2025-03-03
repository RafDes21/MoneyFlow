package com.rafdev.data.repository

import com.rafdev.data.database.dao.ExpenseDao
import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryExpenseImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : RepositoryExpense {
    override fun getExpense(): Flow<List<Expense>> {
        return flow {

        }
    }

}