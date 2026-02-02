package com.rafdev.moneyflow.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun isSameMonth(dateString: String, month: Int, year: Int): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = LocalDateTime.parse(dateString, formatter)

    return date.monthValue == month && date.year == year
}

fun formatMoney(value: Double): String {
    return "$ " + "%,.0f".format(value)
}