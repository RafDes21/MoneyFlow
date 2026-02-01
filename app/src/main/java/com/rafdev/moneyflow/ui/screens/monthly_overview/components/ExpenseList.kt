package com.rafdev.moneyflow.ui.screens.monthly_overview.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.ui.theme.TextPrimary

@Composable
fun ExpenseList(
    expenseList: List<Expense>,
    onEditExpense: (Int) -> Unit,
    onDeleteExpense: (Expense) -> Unit
) {

    LazyColumn {
        items(
            items = expenseList,
            key = { it.id }
        ) { expense ->

            GroupedExpenseItem(
                title = expense.name,
                subtitle = expense.description,
                amount = expense.amount.toString(),
                onEdit = { onEditExpense(expense.id) },
                onDelete = { onDeleteExpense(expense) }
            )

            Divider(
                color = TextPrimary.copy(alpha = 0.08f)
            )
        }
    }
}