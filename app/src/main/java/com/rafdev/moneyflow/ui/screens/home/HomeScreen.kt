package com.rafdev.moneyflow.ui.screens.home

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.ui.components.AlertDialogCustom
import com.rafdev.moneyflow.ui.components.BudgetSummary
import com.rafdev.moneyflow.ui.components.CustomDialog
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.ui.components.TextNumber
import com.rafdev.moneyflow.ui.theme.CustomTypography
import com.rafdev.moneyflow.utils.BudgetLabels
import com.rafdev.moneyflow.utils.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {

    val context = LocalContext.current
    val budget by viewModel.budget.collectAsState()
    val split by viewModel.split.collectAsState()

    val expenseFixed by viewModel.fixedExpensesAmount.collectAsState()
    val expenseRecurrent by viewModel.expensesRecurrent.collectAsState()
    val remainingBudget by viewModel.numericRemainingBudget.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var showDialogAdd by remember { mutableStateOf(false) }
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
            .padding(0.dp, 20.dp, 10.dp, 0.dp)
    ) {
        Text(
            text = Constants.ShortTexts.BUDGET,
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
            TextNumber(
                integer = split.integerPart,
                separator = split.separator,
                decimal = split.decimalPart
            )

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .size(22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Constants.ShortTexts.UPDATE
                )
            }

        }

        if (showDialog) {
            AlertDialogCustom(
                initialValue = budget,
                title = Constants.Labels.NEW_BUDGET_TITLE,
                onDismiss = { showDialog = false })
            {
                val result = viewModel.handleNumberInput(it)
                if (result) {
                    showDialog = false
                } else {
                    showToast = true
                }
            }
        }

        if (showDialogAdd) {
            CustomDialog(
                onDismiss = { showDialogAdd = false }
            ) { title, description, amount, currentDaTime ->
                viewModel.saveExpense(title, description, currentDaTime, amount.toDouble())
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(text = Constants.ShortTexts.SCHEDULED)
        ExpenseCard(
            text = "$ $expenseFixed",
        ) {
            onNavigate()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Constants.ShortTexts.ACTIVITIES,
            )

            IconButton(
                onClick = { showDialogAdd = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ShortTexts.ADD
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
                BudgetSummary(
                    label = BudgetLabels.BUDGET,
                    value = "$ $budget "
                )
                BudgetSummary(
                    label = BudgetLabels.SCHEDULED,
                    value = "$ $expenseFixed"
                )
                BudgetSummary(
                    label = BudgetLabels.ACTIVITIES,
                    value = "$ $expenseRecurrent"
                )
                BudgetSummary(
                    label = BudgetLabels.TOTAL,
                    value = "$ $remainingBudget"
                )
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
                    contentDescription = Constants.ShortTexts.DETAILS
                )
            }
        }
    }
}


