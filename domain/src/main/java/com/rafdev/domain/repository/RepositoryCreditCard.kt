package com.rafdev.domain.repository

import com.rafdev.domain.model.CreditCardDomain
import kotlinx.coroutines.flow.Flow

interface RepositoryCreditCard {

    fun getAllCreditCard(): Flow<Result<List<CreditCardDomain>>>

    suspend fun insertCreditCard(creditCardDomain: CreditCardDomain)

    fun deleteCreditCard(creditCardId: Int)

}