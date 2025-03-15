package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rafdev.moneyflow.utils.Constants
import com.rafdev.moneyflow.utils.formatBudgetInput
import com.rafdev.moneyflow.utils.getCurrentDateTime
import com.rafdev.moneyflow.utils.toFormattedBudget

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
                    onValueChange = { amount = it.toFormattedBudget() },
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