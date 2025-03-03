package com.rafdev.domain.usecase.budget

import com.rafdev.domain.model.Budget
import com.rafdev.domain.repository.RepositoryBudget
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveBudgetUseCase @Inject constructor(private val repository: RepositoryBudget) {

    operator fun invoke(budget: Budget) = repository.insertBudget(budget)

}