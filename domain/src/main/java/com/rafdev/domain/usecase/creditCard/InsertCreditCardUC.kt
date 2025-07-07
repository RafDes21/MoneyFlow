package com.rafdev.domain.usecase.creditCard

import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.repository.RepositoryCreditCard
import javax.inject.Inject

class InsertCreditCardUC @Inject constructor(private val repositoryCreditCard: RepositoryCreditCard) {

    suspend operator fun invoke(creditCardDomain: CreditCardDomain) =
        repositoryCreditCard.insertCreditCard(creditCardDomain)

}