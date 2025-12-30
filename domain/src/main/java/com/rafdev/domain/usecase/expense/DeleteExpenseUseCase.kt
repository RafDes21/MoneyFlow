package com.rafdev.domain.usecase.expense

import com.rafdev.domain.repository.RepositoryExpense
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(private val repository: RepositoryExpense) {
    suspend fun execute(expenseId: Int, creditCardId: Int?) {
        repository.deleteExpenseById(expenseId, creditCardId)
    }
}