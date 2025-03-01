package com.rafdev.data.mapper

import com.rafdev.data.model.entities.BudgetEntity
import com.rafdev.domain.model.Budget

fun BudgetEntity.toUi() = Budget(
    totalBudget = totalBudget
)

fun Budget.toEntity() = BudgetEntity(
    id = 0,
    totalBudget = totalBudget
)
