package com.rafdev.moneyflow.utils

fun String.toFormattedBudget(): Double {
    val cleanedInput = this.replace("[^\\d]".toRegex(), "")
    return cleanedInput.toDoubleOrNull() ?: 0.0
}