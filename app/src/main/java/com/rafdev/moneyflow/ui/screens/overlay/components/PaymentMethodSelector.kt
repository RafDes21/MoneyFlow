package com.rafdev.moneyflow.ui.screens.overlay.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.components.PaymentRadioItem
import com.rafdev.moneyflow.ui.theme.TextSecondary

@Composable
fun PaymentMethodSelector(
    selected: String,
    onSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Método de pago",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PaymentRadioItem(
                label = "Efectivo",
                selected = selected == "CASH",
                onClick = { onSelected("CASH") }
            )
            PaymentRadioItem(
                label = "Débito",
                selected = selected == "DEBIT",
                onClick = { onSelected("DEBIT") }
            )
            PaymentRadioItem(
                label = "Crédito",
                selected = selected == "CREDIT",
                onClick = { onSelected("CREDIT") }
            )
        }
    }
}

@Preview
@Composable
fun PaymentMethodSelectorPreview() {
    PaymentMethodSelector(
        selected = "CREDIT",
        onSelected = {}
    )
}
