package com.rafdev.domain.usecase.expense

import com.rafdev.domain.model.Expense
import com.rafdev.domain.repository.RepositoryExpense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseUseCase @Inject constructor(private val repository : RepositoryExpense) {

    operator fun invoke (): Flow<Result<List<Expense>>> = repository.getExpense()
}