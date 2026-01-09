package com.rafdev.data.mapper.event

import com.rafdev.data.entities.event.EventEntity
import com.rafdev.data.entities.event.EventExpenseEntity
import com.rafdev.domain.model.event.Event
import com.rafdev.domain.model.event.EventExpense

fun EventExpenseEntity.toDomain(): EventExpense {
    return EventExpense(
        id = id,
        name = name,
        amount = amount,
    )
}

fun EventEntity.toDomain(
    expenses: List<EventExpenseEntity>
): Event {

    val total = expenses.sumOf { it.amount }

    return Event(
        id = id,
        title = title,
        budget = budget,
        totalSpent = total,
        expenses = expenses.map { it.toDomain() }
    )
}
