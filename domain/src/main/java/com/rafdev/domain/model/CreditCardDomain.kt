package com.rafdev.domain.model

data class CreditCardDomain(
    val type: Int,
    val title: String,
    val number: String,
    val total: Double,
    val color: Int
)
