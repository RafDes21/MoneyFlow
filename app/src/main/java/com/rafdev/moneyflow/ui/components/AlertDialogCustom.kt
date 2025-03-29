package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.rafdev.moneyflow.utils.Constants

@Composable
fun AlertDialogCustom(
    initialValue: String,
    title: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    if (it.matches(Regex("^[0-9,.]*$"))) {
                        text = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(Constants.Operations.CANCEL)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(text)
                }
            ) {
                Text(Constants.Operations.CREATE)
            }
        }
    )

}

