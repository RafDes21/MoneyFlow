package com.rafdev.moneyflow.utils

import com.rafdev.moneyflow.ui.screens.planned.UiMonth

fun String.toFormattedBudget(): Double {
    val cleanedInput = this.replace("[^\\d]".toRegex(), "")
    return cleanedInput.toDoubleOrNull() ?: 0.0
}