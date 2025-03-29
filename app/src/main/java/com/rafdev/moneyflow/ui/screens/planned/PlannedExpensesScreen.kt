package com.rafdev.moneyflow.ui.screens.planned

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.ui.components.CustomDialog
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.utils.Constants
import com.rafdev.moneyflow.utils.formatBudgetInput
import com.rafdev.moneyflow.utils.getCurrentDateTime
import com.rafdev.moneyflow.utils.toFormattedBudget
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
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
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = Constants.Labels.EXPENSES_SCHEDULED,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Row(modifier = Modifier
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
                        description = expense.description,
                        time = "",
                        amount = expense.amount.toString(),
                        date = "",
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
                result?.let{
                    viewModel.saveExpense(title, description, currentDaTime, it)
                    showDialog = false
                }?: run {
                    showToast = true
                }

            }
        }

    }
}





