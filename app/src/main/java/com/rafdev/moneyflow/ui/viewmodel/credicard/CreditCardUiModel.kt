package com.rafdev.moneyflow.ui.viewmodel.credicard

data class CreditCardUiModel(
    val title: String,
    val numberMasked: String,
    val totalFormatted: Double,
    val color: Int,
    val type: Int
)