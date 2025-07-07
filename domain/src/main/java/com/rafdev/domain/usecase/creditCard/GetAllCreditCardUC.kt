package com.rafdev.domain.usecase.creditCard

import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.repository.RepositoryCreditCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCreditCardUC @Inject constructor(private val repositoryCreditCard: RepositoryCreditCard) {

    operator fun invoke(): Flow<Result<List<CreditCardDomain>>> = repositoryCreditCard.getAllCreditCard()

}