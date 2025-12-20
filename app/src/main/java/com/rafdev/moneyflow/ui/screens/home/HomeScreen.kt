package com.rafdev.moneyflow.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.CustomDialog
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.ui.components.ExpenseDetailBottomSheet
import com.rafdev.moneyflow.ui.components.FloatingCard
import com.rafdev.moneyflow.ui.screens.home.components.ExpenseCardFixed
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.card.UIKitCard
import com.rafdev.moneyflow.ui.uikit.text.UIKitText
import com.rafdev.moneyflow.utils.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
    activeBottomSheet: () -> Unit
) {

    val context = LocalContext.current
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val totalFixedExpenses by viewModel.totalFixedExpenses.collectAsState()
    val totalRecurrentExpenses by viewModel.totalRecurrentExpenses.collectAsState()

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
            .background(Background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)

        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    UIKitText(
                        text = Constants.ShortTexts.TOTAL,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    UIKitText(
                        text = "$ $totalExpenses"
                    )

                }
                Spacer(modifier = Modifier.height(50.dp))

            }
            item {
                ActionTitleItem(
                    modifier = Modifier.padding(bottom = 8.dp),
                    title = "PROGRAMADOS",
                    iconRes = R.drawable.ic_add,
                ) {
                    activeBottomSheet()
                }
                UIKitCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigate() }
                ) {
                    UIKitText(
                        modifier = Modifier.padding(16.dp),
                        text = "$ $totalFixedExpenses"
                    )
                }

                ActionTitleItem(
                    title = "ACTIVIDADES",
                    iconRes = R.drawable.ic_add,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),

                    ) {
                    showDialogAdd = true
                }

            }

            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->
                    if (expense.type == "recurring") {
                        val dateTimeParts = expense.date.split(" ")
                        val date = dateTimeParts.getOrNull(0) ?: ""
                        val time = dateTimeParts.getOrNull(1) ?: ""

                        ExpenseCard(
                            title = expense.name,
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
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }


        }
        FloatingCard(
            text = totalRecurrentExpenses,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

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



