package com.rafdev.data.repository

import com.rafdev.data.database.dao.CreditCardDao
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.repository.RepositoryCreditCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryCreditCardImpl @Inject constructor(private val creditCardDao: CreditCardDao) :
    RepositoryCreditCard {
    override fun getAllCreditCard(): Flow<Result<List<CreditCardDomain>>> {
        TODO("Not yet implemented")
    }

    override fun insertCreditCard(creditCardDomain: CreditCardDomain) {
        TODO("Not yet implemented")
    }

    override fun deleteCreditCard(creditCardId: Int) {
        TODO("Not yet implemented")
    }
}