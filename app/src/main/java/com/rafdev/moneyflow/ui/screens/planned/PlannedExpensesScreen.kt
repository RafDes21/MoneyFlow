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
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.utils.Constants
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlannedExpensesScreen(viewModel: PlannedExpensesViewModel = hiltViewModel()) {

    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = Constants.EXPENSES_SCHEDULED,
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
                Text(text = Constants.ADD)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_ACTIVITY
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
                viewModel.saveExpense(title, description, currentDaTime, amount)
            }
        }

    }
}

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, Double, String) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableDoubleStateOf(0.0) }

    val formatAmount = formatBudgetInput(amount)

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = Constants.ADD_ACTIVITY
                        )
                    }

                    Text(
                        text = Constants.NEW_FIXED_EXPENSE,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                    )
                }


                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Constants.LABEL_TITLE },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Constants.LABEL_DESCRIPTION },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = formatAmount,
                    onValueChange = { amount = formatBudgetInputReverse(it) },
                    label = { Constants.LABEL_AMOUNT },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            val currentDateTime = getCurrentDateTime()
                            onSave(title, description, amount, currentDateTime)
                            onDismiss()
                        }
                    ) {
                        Text(Constants.SAVE)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text(Constants.CANCEL)
                    }
                }
            }
        }
    }
}

private fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

fun formatBudgetInput(input: Double): String {
    return try {
        if (input >= 1000) {
            val formatter = DecimalFormat("#,###")
            formatter.format(input)
        } else {
            input.toString()
        }
    } catch (e: Exception) {
        input.toString()
    }
}

fun formatBudgetInputReverse(input: String): Double {
    val cleanedInput = input.replace("[^\\d]".toRegex(), "")
    return cleanedInput.toDoubleOrNull() ?: 0.0
}

