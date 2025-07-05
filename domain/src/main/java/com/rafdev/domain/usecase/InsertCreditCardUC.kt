package com.rafdev.domain.usecase

import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.repository.RepositoryCreditCard
import javax.inject.Inject

class InsertCreditCardUC @Inject constructor(private val repositoryCreditCard: RepositoryCreditCard) {

    operator fun invoke(creditCardDomain: CreditCardDomain) =
        repositoryCreditCard.insertCreditCard(creditCardDomain)

}