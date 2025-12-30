package com.rafdev.data.repository

import com.rafdev.data.database.dao.CreditCardDao
import com.rafdev.data.database.dao.ExpenseDao
import com.rafdev.data.mapper.toDomain
import com.rafdev.data.mapper.toEntity
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.repository.RepositoryCreditCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryCreditCardImpl @Inject constructor(
    private val creditCardDao: CreditCardDao,
    private val expenseDao: ExpenseDao
) : RepositoryCreditCard {

    override fun getAllCreditCard(): Flow<Result<List<CreditCardDomain>>> {
        return creditCardDao.getAllCreditCard()
            .map { list ->
                Result.success(list.map { it.toDomain() })
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override suspend fun insertCreditCard(creditCardDomain: CreditCardDomain) {
        creditCardDao.insertCreditCard(creditCardDomain.toEntity())
    }

    override suspend fun deleteCreditCard(creditCardId: Int) {
        creditCardDao.deleteCreditCard(creditCardId)
    }
}