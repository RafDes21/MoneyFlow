package com.rafdev.domain.usecase.expense

import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val repository: RepositoryExpense
) {
    suspend operator fun invoke(id: Int): Result<Expense> =
        repository.getExpenseById(id)
}
