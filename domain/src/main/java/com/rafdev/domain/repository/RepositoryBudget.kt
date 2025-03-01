package com.rafdev.domain.repository

import com.rafdev.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface RepositoryBudget {

    fun getBudget() : Flow<Budget>

    fun insertBudget(budget: Budget)

}