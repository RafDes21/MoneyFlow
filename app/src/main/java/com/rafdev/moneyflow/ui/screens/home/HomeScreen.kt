package com.rafdev.moneyflow.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafdev.domain.model.Budget
import com.rafdev.moneyflow.ui.components.BudgetSummary
import com.rafdev.moneyflow.ui.components.CustomDialog
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.ui.theme.CustomTypography
import com.rafdev.moneyflow.utils.BudgetLabels
import com.rafdev.moneyflow.utils.Constants
import com.rafdev.moneyflow.utils.formatBudgetInput
import com.rafdev.moneyflow.utils.toFormattedBudget
import java.text.DecimalFormat

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {

    val budget by viewModel.budget.collectAsState()
    val expense by viewModel.fixedExpensesAmount.collectAsState()
    val remainingBudget by viewModel.remainingBudget.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDialogAdd by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 20.dp, 10.dp, 0.dp)
    ) {
        Text(
            text = Constants.BUDGET,
            style = CustomTypography.titleLarge,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$ ${formatBudgetInput(budget)}",
                style = CustomTypography.titleLarge,
            )

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .size(22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Constants.EDIT_BUDGET
                )
            }

        }

        if (showDialog) {
            BudgetEditDialog(
                currentBudget = budget,
                onDismiss = { showDialog = false },
                onSave = { newBudget ->
                    viewModel.updateBudget(Budget(newBudget))
                    showDialog = false
                }
            )
        }

        if (showDialogAdd) {
            CustomDialog(
                onDismiss = { showDialogAdd = false }
            ) { title, description, amount, currentDaTime ->
                viewModel.saveExpense(title, description, currentDaTime, amount)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(text = Constants.EXPENSES_SCHEDULED)
        ExpenseCard(
            text = "$ ${formatBudgetInput(expense)}",
        ) {
            onNavigate()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Constants.ACTIVITIES,
            )

            IconButton(
                onClick = {showDialogAdd = true}
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_ACTIVITY
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->

                    val dateTimeParts = expense.date.split(" ")
                    val date = dateTimeParts.getOrNull(0) ?: ""
                    val time = dateTimeParts.getOrNull(1) ?: ""

                    ExpenseCard(
                        title = expense.name,
                        description = expense.description,
                        time = time,
                        amount = expense.amount.toString(),
                        date = date,
                        onUpdate = { /* Acción para actualizar */ },
                        onDelete = {
                            viewModel.deleteExpenseById(expense.id)
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BudgetSummary(label = BudgetLabels.BUDGET, value = "$ ${formatBudgetInput(budget)}")
                BudgetSummary(label = BudgetLabels.SCHEDULED, value = "$ ${formatBudgetInput(expense)}")
                BudgetSummary(label = BudgetLabels.ACTIVITIES, value = "$ ${formatBudgetInput(budget)}")
                BudgetSummary(label = BudgetLabels.TOTAL, value = "$ ${formatBudgetInput(remainingBudget)}")
            }
        }

    }

}

@Composable
fun ExpenseCard(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
            )

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = Constants.DETAILS
                )
            }
        }
    }
}

@Composable
fun BudgetEditDialog(
    currentBudget: Double,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var newBudget by remember { mutableDoubleStateOf(currentBudget) }

    val formattedBudget = formatBudgetInput(newBudget)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Constants.NEW_BUDGET_TITLE) },
        text = {
            TextField(
                value = formattedBudget,
                onValueChange = {
                    newBudget = it.toFormattedBudget()
                },
                label = { Text(text = Constants.NEW_BUDGET_TITLE) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(newBudget) }) {
                Text(Constants.SAVE_BUTTON_TEXT)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(Constants.CANCEL_BUTTON_TEXT)
            }
        }
    )
}

