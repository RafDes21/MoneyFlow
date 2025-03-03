package com.rafdev.data.repository

import android.util.Log
import com.rafdev.data.database.dao.BudgetDao
import com.rafdev.data.mapper.toEntity
import com.rafdev.data.mapper.toUi
import com.rafdev.data.model.entities.BudgetEntity
import com.rafdev.domain.model.Budget
import com.rafdev.domain.repository.RepositoryBudget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryBudgetImpl @Inject constructor(
    private val budgetDao: BudgetDao
) : RepositoryBudget {

    override fun getBudget(): Flow<Budget> {
        return flow {
            val budget = budgetDao.getAllBudget()
            budget.collect{
                it?.let {
                    emit(it.toUi())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun insertBudget(budget: Budget) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                budgetDao.insertBudget(budget.toEntity())
            }catch (e:Exception){
                Log.e("RepositoryBudgetImpl", "Error al insertar el presupuesto: ${e.message}", e)
            }

        }
    }
}
