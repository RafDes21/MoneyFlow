package com.rafdev.data.repository

import com.rafdev.data.dao.CreditCardDao
import com.rafdev.data.dao.ExpenseDao
import com.rafdev.data.mapper.toDb
import com.rafdev.data.mapper.toUi
import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryExpenseImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val creditCardDao: CreditCardDao
) : RepositoryExpense {

    override suspend fun getExpenseById(id: Int): Result<Expense> =
        runCatching {
            val entity = expenseDao.getExpenseById(id)
            entity.toUi()
        }

    override fun getExpense(): Flow<Result<List<Expense>>> {
        return expenseDao.getAllExpenses()
            .map { entities ->
                Result.success(entities.map { it.toUi() })
            }
            .catch { e ->
                emit(Result.failure(e))
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun insertExpense(expense: Expense): Result<Unit> {
        return runCatching {
            expenseDao.insertExpense(expense.toDb())

            expense.creditCardId?.let { cardId ->
                val total = expenseDao
                    .getTotalByCreditCard(cardId) ?: 0.0

                creditCardDao.updateTotal(cardId, total)
            }
        }
    }

    override suspend fun deleteExpenseById(
        expenseId: Int,
        creditCardId: Int?
    ) {
        expenseDao.deleteExpenseById(expenseId)

        creditCardId?.let { cardId ->
            val total = expenseDao
                .getTotalByCreditCard(cardId) ?: 0.0

            creditCardDao.updateTotal(cardId, total)
        }
    }

}