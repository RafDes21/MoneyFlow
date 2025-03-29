package com.rafdev.moneyflow.utils

import com.rafdev.moneyflow.ui.screens.home.SplitNumber
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

class NumberFormatter @Inject constructor() {
    fun parseAndFormatToDouble(input: String): Double? {
        try {
            val cleanedInput = input.trim()

            val lastDot = input.lastIndexOf(".")
            val lastComma = input.lastIndexOf(",")

            val lastSeparatorIndex = maxOf(lastDot, lastComma)

            if (lastSeparatorIndex == -1) {
                return cleanedInput.toDouble()
            }

            val numberBeforeDecimal =
                cleanedInput.substring(0, lastSeparatorIndex)
                    .replace(".", "")
                    .replace(",", "")
            val decimalPart =
                cleanedInput.substring(lastSeparatorIndex + 1)
            val isComma = cleanedInput[lastSeparatorIndex] == ','


            var formattedDecimal = decimalPart

            if (formattedDecimal.length == 3 && !isComma) {
                val final = "$numberBeforeDecimal$formattedDecimal.0"
                return final.toDouble()
            }
            if (formattedDecimal.length > 2) {
                formattedDecimal = formattedDecimal.substring(0, 2)
            }

            val finalString = "$numberBeforeDecimal.$formattedDecimal"
            return finalString.toDouble()
        } catch (e: Exception) {
            return null
        }
    }

    fun formatToString(value: Double): String {
        val locale = Locale.getDefault()
        val symbols = DecimalFormatSymbols(locale)
        val format = DecimalFormat("#,##0.00", symbols)
        return format.format(value)
    }

    fun splitNumBer(text: String):SplitNumber {
        val lastDot = text.lastIndexOf(".")
        val lastComma = text.lastIndexOf(",")

        val lastSeparatorIndex = maxOf(lastDot, lastComma)
        val isSeparator = text[lastSeparatorIndex]

        val integerPart = text.substring(0, lastSeparatorIndex)
        val decimalPart = text.substring(lastSeparatorIndex + 1)

        return SplitNumber(
            integerPart = integerPart,
            decimalPart = decimalPart,
            separator = isSeparator.toString()
        )

    }
}

