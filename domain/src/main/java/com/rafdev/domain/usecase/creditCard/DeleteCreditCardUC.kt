package com.rafdev.domain.usecase.creditCard

import com.rafdev.domain.repository.RepositoryCreditCard
import javax.inject.Inject

class DeleteCreditCardUC @Inject constructor(private val repositoryCreditCard: RepositoryCreditCard) {

    suspend operator fun invoke(id: Int) =
        repositoryCreditCard.deleteCreditCard(id)

}