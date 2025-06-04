package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.ui.theme.CardPalette
import com.rafdev.moneyflow.ui.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailBottomSheet(
    expense: Expense,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Palette.CardColor)
                .padding(24.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título y Monto
            Text(
                text = expense.name,
                color = CardPalette.Title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (expense.amount >= 0)
                    "+$${expense.amount}" else "-$${expense.amount}",
                color = if (expense.amount >= 0)
                    CardPalette.AmountPositive else CardPalette.AmountNegative,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Divider(color = Palette.BoxBackground)

            // Datos adicionales
            ExpenseInfoItem(label = "Fecha", value = expense.date)
            ExpenseInfoItem(label = "Categoría", value = expense.category)
            ExpenseInfoItem(label = "Método de pago", value = expense.paymentMethod)
            ExpenseInfoItem(label = "Tipo", value = expense.type)
            ExpenseInfoItem(label = "Recurrente", value = if (expense.recurring) "Sí" else "No")
            if (expense.recurring) {
                ExpenseInfoItem(label = "Período", value = expense.period)
            }
            ExpenseInfoItem(label = "Descripción", value = expense.description)
            ExpenseInfoItem(label = "Notas", value = expense.notes)
            ExpenseInfoItem(label = "Pagado", value = if (expense.isPaid) "Sí" else "No")
        }
    }
}

@Composable
fun ExpenseInfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = CardPalette.Description,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            color = CardPalette.Title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpenseDetail() {
    val example = Expense(
        id = 1,
        name = "Cena en restaurante",
        amount = 45.0,
        type = "Gasto",
        description = "Cena con amigos",
        image = "",
        color = "#FF5733",
        date = "2024-06-04",
        category = "Comida",
        recurring = false,
        period = "",
        paymentMethod = "Tarjeta",
        notes = "Usé la tarjeta de débito",
        isPaid = true
    )
    ExpenseDetailBottomSheet(expense = example, onDismiss = {})
}

