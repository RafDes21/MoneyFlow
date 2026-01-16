package com.rafdev.moneyflow.ui.screens.planned

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.screens.planned.components.GroupedExpenseItem
import com.rafdev.moneyflow.ui.screens.planned.components.MonthNavigator
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.text.UIKitText


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

    var showDialogApp by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {
        MonthNavigator(
            month = month,
            onPrevious = { previous() },
            onNext = { nextMonth() },
        )
        UIKitText(
            text = stringResource(R.string.salary_section_title)
        )
        ActionTitleItem(
            title = stringResource(R.string.add_salary),
            iconRes = UIKitIcons.Add,
            iconPosition = IconPosition.START
        ) {

        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            ActionTitleItem(
                title = "Agregar",
                iconRes = UIKitIcons.Add,
                onClick = onAddExpense,
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    name = "Planned Expenses Preview"
)
@Composable
fun PlannedExpensesContentPreview() {
    PlannedExpensesContent(
        state = ExpenseState(
            success = listOf(
                Expense(
                    id = 1,
                    name = "Alquiler",
                    amount = 120000.0,
                    type = "Fijo",
                    description = "Departamento",
                    image = "",
                    color = "#4CAF50",
                    date = "2026-01-01",
                    category = "Vivienda",
                    recurring = true,
                    period = "Mensual",
                    paymentMethod = "Transferencia",
                    notes = "",
                    isPaid = false,
                    creditCardId = null
                ),
                Expense(
                    id = 2,
                    name = "Internet",
                    amount = 15000.0,
                    type = "Servicio",
                    description = "Fibra óptica",
                    image = "",
                    color = "#2196F3",
                    date = "2026-01-05",
                    category = "Servicios",
                    recurring = true,
                    period = "Mensual",
                    paymentMethod = "Débito",
                    notes = "Vence el 10",
                    isPaid = true,
                    creditCardId = null
                )
            )
        ),
        month = UiMonth(
            month = 1,
            year = 2026
        ),
        onAddExpense = {},
        onEditExpense = {},
        onDeleteExpense = { _, _ -> },
        previous = {},
        nextMonth = {}
    )
}



