package com.rafdev.moneyflow.utils

import com.rafdev.moneyflow.ui.screens.planned.UiMonth

fun String.toFormattedBudget(): Double {
    val cleanedInput = this.replace("[^\\d]".toRegex(), "")
    return cleanedInput.toDoubleOrNull() ?: 0.0
}

fun UiMonth.next(): UiMonth =
    if (month == 12) UiMonth(year + 1, 1)
    else UiMonth(year, month + 1)

fun UiMonth.previous(): UiMonth =
    if (month == 1) UiMonth(year - 1, 12)
    else UiMonth(year, month - 1)