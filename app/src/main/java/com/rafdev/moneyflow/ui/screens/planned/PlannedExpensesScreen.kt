package com.rafdev.moneyflow.ui.screens.planned

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.screens.planned.components.GroupedExpenseItem
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons


@Composable
fun PlannedExpensesScreen(
    viewModel: PlannedExpensesViewModel = hiltViewModel(),
    onAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit
) {
    val state by viewModel.state.collectAsState()

    PlannedExpensesContent(
        state = state,
        onAddExpense = onAddExpense,
        onEditExpense = onEditExpense,
        onDeleteExpense = viewModel::deleteExpenseById
    )
}


@Composable
fun PlannedExpensesContent(
    state: ExpenseState,
    onAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Int) -> Unit
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    var showDialogApp by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Error en el valor ingresado", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            ActionTitleItem(
                title = "Agregar",
                iconRes = UIKitIcons.Add,
                onClick = onAddExpense,
                pushIconToEnd = false
            )
        }

        LazyColumn {
            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->
                    GroupedExpenseItem(
                        title = expense.name,
                        subtitle = expense.description,
                        amount = expense.amount.toString(),
                        onEdit = { onEditExpense(expense) },
                        onDelete = {
                            expenseToDelete = expense
                            showDialogApp = true
                        }
                    )
                    Divider(
                        color = TextPrimary.copy(alpha = 0.08f)
                    )
                }
            }
        }

        if (showDialogApp) {
            DialogApp(
                title = "Eliminar",
                description = "¿Estás seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.",
                onConfirm = {
                    onDeleteExpense(expenseToDelete?.id!!)
                    expenseToDelete = null
                    showDialogApp = false
                },
                onDismiss = {
                    expenseToDelete = null
                    showDialogApp = false
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlannedExpensesScreenPreview() {
    PlannedExpensesContent(
        state = ExpenseState(
            success = listOf(
                Expense(
                    id = 0,
                    name = "name",
                    amount = 0.0,
                    type = "fixed",
                    description = "description",
                    image = "",
                    color = "",
                    date = "currentDateTime",
                    category = "",
                    recurring = false,
                    period = "",
                    paymentMethod = "",
                    notes = "",
                    isPaid = false
                ),
                Expense(
                    id = 1,
                    name = "name",
                    amount = 0.0,
                    type = "fixed",
                    description = "description",
                    image = "",
                    color = "",
                    date = "currentDateTime",
                    category = "",
                    recurring = false,
                    period = "",
                    paymentMethod = "",
                    notes = "",
                    isPaid = false
                )
            )
        ),
        onAddExpense = {},
        onEditExpense = {},
        onDeleteExpense = {}
    )
}





