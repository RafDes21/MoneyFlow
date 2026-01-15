package com.rafdev.moneyflow.ui.screens.planned

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.rafdev.domain.model.Expense
import com.rafdev.domain.model.salary.Month
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
    onEditExpense: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    PlannedExpensesContent(
        state = state,
        month = month,
        onAddExpense = onAddExpense,
        onEditExpense = onEditExpense,
        onDeleteExpense = viewModel::deleteExpenseById,
        previous = viewModel::previousMonth,
        nextMonth = viewModel::nextMonth
    )
}


@Composable
fun PlannedExpensesContent(
    state: ExpenseState,
    month: UiMonth,
    onAddExpense: () -> Unit,
    onEditExpense: (Int) -> Unit,
    onDeleteExpense: (Int, Int?) -> Unit,
    previous: () -> Unit,
    nextMonth: () -> Unit
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
        Row {
            IconButton(onClick = previous) {
                Icon(Icons.Default.ArrowBack, null)
            }

            Text(
                text = "${(month.month)} ${month.year}"
            )

            IconButton(onClick = nextMonth) {
                Icon(Icons.Default.ArrowForward, null)
            }
        }
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
                        onEdit = { onEditExpense(expense.id) },
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
                    onDeleteExpense(expenseToDelete?.id!!, expenseToDelete?.creditCardId)
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




