package com.rafdev.domain.usecase.expense

import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import javax.inject.Inject

class InsertExpenseUseCase @Inject constructor(private val repositoryExpense: RepositoryExpense) {

    suspend operator fun invoke(expense: Expense): Result<Unit> =
        repositoryExpense.insertExpense(expense)

}