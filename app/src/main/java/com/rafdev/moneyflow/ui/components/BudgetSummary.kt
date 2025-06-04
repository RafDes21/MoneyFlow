package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.Palette

@Composable
fun BudgetSummary(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Palette.TextOnDark,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        Text(text = value)
    }
}