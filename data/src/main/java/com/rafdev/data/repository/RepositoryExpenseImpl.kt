package com.rafdev.data.repository

import android.util.Log
import com.rafdev.data.database.dao.ExpenseDao
import com.rafdev.data.mapper.toDb
import com.rafdev.data.mapper.toEntity
import com.rafdev.data.mapper.toUi
import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryExpenseImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : RepositoryExpense {
    override fun getExpense(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
            .map { listOfEntities ->
                listOfEntities.map { it.toUi() }
            }
            .flowOn(Dispatchers.IO)
    }

     override suspend fun insertExpense(expense: Expense) {
         try {
             expenseDao.insertExpense(expense.toDb())
         } catch (e: Exception) {
             Log.e(
                 "RepositoryExpenseImpl",
                 "Error al insertar el gasto: ${e.message}",
                 e
             )
             throw e
         }
    }

    override fun deleteExpenseById(expenseId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                expenseDao.deleteExpenseById(expenseId)
            }catch (e:Exception){
                Log.e("RepositoryBudgetImpl", "Error al eliminar: ${e.message}", e)
            }

        }
    }

}