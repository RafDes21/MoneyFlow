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
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.screens.planned.components.GroupedExpenseItem
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons


/*@Composable
fun PlannedExpensesScreen(viewModel: PlannedExpensesViewModel = hiltViewModel()) {

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Error en el valor ingresado", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier
                    .clickable { showDialog = true }
            ) {
                Text(text = Constants.ShortTexts.ADD)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.Labels.NEW_BUDGET_TITLE
                )
            }
        }

        LazyColumn {
            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->

                    val dateTimeParts = expense.date.split(" ")
                    val date = dateTimeParts.getOrNull(0) ?: ""
                    val time = dateTimeParts.getOrNull(1) ?: ""

                    ExpenseCard(
                        title = expense.name,
                        time = "",
                        amount = expense.amount.toString(),
                        date = "",
                        onDetail = {},
                        onUpdate = { /* Acción para actualizar */ },
                        onDelete = {
                            viewModel.deleteExpenseById(expense.id)
                        }
                    )
                }
            }
        }


        if (showDialog) {
            CustomDialog(
                onDismiss = { showDialog = false }
            ) { title, description, amount, currentDaTime ->
                val result = viewModel.handleNumberInput(amount)
                result?.let {
                    viewModel.saveExpense(title, description, currentDaTime, it)
                    showDialog = false
                } ?: run {
                    showToast = true
                }

            }
        }

    }
}*/


@Composable
fun PlannedExpensesScreen(
    viewModel: PlannedExpensesViewModel = hiltViewModel(),
    onOpenSheet: (SheetMode) -> Unit
) {
    val state by viewModel.state.collectAsState()

    PlannedExpensesContent(
        state = state,
        onOpenSheet = onOpenSheet,
        onDelete = { id ->
            //viewModel.deleteExpenseById(id)
        }
    )
}


@Composable
fun PlannedExpensesContent(
    state: ExpenseState,
    onOpenSheet: (SheetMode) -> Unit,
    onDelete: (Long) -> Unit
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

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
                onClick = { onOpenSheet(SheetMode.ADD)},
                pushIconToEnd = false
            )
        }

        LazyColumn {
            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->
                    GroupedExpenseItem(
                        title = expense.name,
                        subtitle = "",
                        amount = expense.amount.toString(),
                        onEdit = {},
                        onDelete = {}
                    )
                    Divider(
                        color = TextPrimary.copy(alpha = 0.08f)
                    )
                }
            }
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
        onOpenSheet = {},
        onDelete = {}
    )
}





