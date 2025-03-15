package com.rafdev.moneyflow.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

fun formatBudgetInput(input: Double): String {
    return try {
        if (input >= 1000) {
            val formatter = DecimalFormat("#,###")
            formatter.format(input)
        } else {
            input.toString()
        }
    } catch (e: Exception) {
        input.toString()
    }
}