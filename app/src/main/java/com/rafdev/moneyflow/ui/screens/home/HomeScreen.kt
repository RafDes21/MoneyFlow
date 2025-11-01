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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.ui.components.AlertDialogCustom
import com.rafdev.moneyflow.ui.components.BudgetSummary
import com.rafdev.moneyflow.ui.components.CustomDialog
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.ui.components.ExpenseDetailBottomSheet
import com.rafdev.moneyflow.ui.components.TextNumber
import com.rafdev.moneyflow.ui.screens.home.components.ExpenseCardFixed
import com.rafdev.moneyflow.ui.theme.CustomTypography
import com.rafdev.moneyflow.ui.theme.Palette
import com.rafdev.moneyflow.utils.BudgetLabels
import com.rafdev.moneyflow.utils.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
    activeBottomSheet: () -> Unit
) {

    val context = LocalContext.current
    val splitRecurrent by viewModel.splitRecurrent.collectAsState()
    val splitFixed by viewModel.splitFixed.collectAsState()
    val total by viewModel.total.collectAsState()

    val expenseFixed by viewModel.fixedExpensesAmount.collectAsState()
    val expenseRecurrent by viewModel.expensesRecurrent.collectAsState()
    val remainingBudget by viewModel.numericRemainingBudget.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var showDialogAdd by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialogApp by remember { mutableStateOf(false) }
    var expenseItem by remember { mutableStateOf<Expense?>(null) }

    val state by viewModel.state.collectAsState()

    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Error en el valor ingresado", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette.BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = Constants.ShortTexts.TOTAL,
                color = Palette.TextColor,
                style = CustomTypography.titleLarge,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextNumber(
                    integer = total.integerPart,
                    separator = total.separator,
                    decimal = total.decimalPart
                )

            }

            if (showDialogAdd) {
                CustomDialog(
                    onDismiss = { showDialogAdd = false }
                ) { title, description, amount, currentDaTime ->
                    viewModel.saveExpense(title, description, currentDaTime, amount.toDouble())
                }
            }

            if (showDialogApp) {
                DialogApp(
                    title = "Eliminar",
                    description = "¿Estás seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.",
                    onConfirm = {
                        expenseItem?.id?.let {
                            viewModel.deleteExpenseById(it)
                        }
                        showDialogApp = false
                    },
                    onDismiss = { showDialogApp = false }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = Constants.ShortTexts.SCHEDULED,
                color = Palette.TextColor
            )
            ExpenseCardFixed(
                splitFixed,
                showBottomSheet = activeBottomSheet
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
                    color = Palette.TextColor
                )

                IconButton(
                    onClick = { showDialogAdd = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Palette.ActiveIconColor,
                        contentDescription = Constants.ShortTexts.ADD
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                state.success?.let { expenses ->
                    items(expenses, key = { it.id }) { expense ->
                        if (expense.type == "recurring") {
                            val dateTimeParts = expense.date.split(" ")
                            val date = dateTimeParts.getOrNull(0) ?: ""
                            val time = dateTimeParts.getOrNull(1) ?: ""

                            ExpenseCard(
                                title = expense.name,
                                description = expense.description,
                                time = time,
                                amount = expense.amount.toString(),
                                date = date,
                                onDetail = {
                                    showBottomSheet = true
                                    expenseItem = expense
                                },
                                onUpdate = { /* Acción para actualizar */ },
                                onDelete = {
                                    showDialogApp = true
                                    expenseItem = expense
                                }
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Palette.CardColor)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Constants.TOTAL
                )
                TextNumber(
                    integer = splitRecurrent.integerPart,
                    separator = splitRecurrent.separator,
                    decimal = splitRecurrent.decimalPart,
                    integerSize = 17.sp,
                    decimalSize = 12.sp,
                    horizontalArrangement = Arrangement.Start
                )

            }

        }
        if (showBottomSheet) {
            expenseItem?.let {
                ExpenseDetailBottomSheet(
                    expense = it
                ) {
                    showBottomSheet = false
                }
            }

        }
    }

}



