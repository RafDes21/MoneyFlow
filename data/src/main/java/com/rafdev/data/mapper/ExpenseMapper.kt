package com.rafdev.data.mapper

import com.rafdev.data.entities.ExpenseEntity
import com.rafdev.domain.model.Expense

fun ExpenseEntity.toUi() = Expense(
    id = id,
    name = name ?: "",
    amount = amount ?: 0.0,
    type = type ?: "",
    description = description ?: "",
    image = image ?: "",
    color = color ?: "",
    date = date ?: "",
    category = category ?: "",
    recurring = recurring ?: false,
    period = period ?: "",
    paymentMethod = paymentMethod ?: "",
    notes = notes ?: "",
    isPaid = isPaid ?: false,
    creditCardId = creditCardId
)

fun Expense.toDb() = ExpenseEntity(
    id = id,
    name = name,
    description = description,
    amount = amount,
    date = date,
    type = type,
    paymentMethod = paymentMethod,
    creditCardId = creditCardId
)