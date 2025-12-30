package com.rafdev.domain.model

data class Expense(
    val id: Int,
    val name: String,
    val amount: Double,
    val type: String,
    val description: String,
    val image: String,
    val color: String,
    val date: String,
    val category: String,
    val recurring: Boolean,
    val period: String,
    val paymentMethod: String,
    val notes: String,
    val isPaid: Boolean,
    val creditCardId:   Int?
)