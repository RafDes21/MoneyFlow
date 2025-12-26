package com.rafdev.moneyflow.ui.screens.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.ui.theme.AccentActive
import com.rafdev.moneyflow.ui.theme.AccentInactive
import com.rafdev.moneyflow.ui.uikit.button.UIKitButton
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.input.UIKitInput
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormContent(
    viewModel: ExpenseFormOverlayViewModel = hiltViewModel(),
    onClose: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                UIKitIcon(
                    iconRes = UIKitIcons.close,
                    contentDescription = "Cerrar",
                    onClick = { onClose() }
                )

                UIKitText(
                    text = "Agregar / Editar gasto",
                )
            }

            Divider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    UIKitInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = { title = it },
                        label = "Título"
                    )
                    Spacer(Modifier.height(12.dp))

                }

                item {
                    UIKitInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = { description = it },
                        label = "Descipción"
                    )
                    Spacer(Modifier.height(12.dp))

                }

                item {
                    UIKitInput(
                        value = amount,
                        onValueChange = { amount = it },
                        label = "Monto",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.height(12.dp))

                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    CircleSelector(
                        options = listOf("CASH", "DEBIT", "CREDIT"),
                        selectedOption = viewModel.paymentMethod,
                        onOptionSelected = { viewModel.paymentMethod = it })
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {

                    Spacer(Modifier.height(30.dp))

                    UIKitButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {}
                    ) {
                        UIKitText(text = "Guardar")
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun AddEditFixedExpenseOverlayPreview() {
    ExpenseFormContent(onClose = {})
}

@Composable
fun CircleSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            UIKitButton(
                background = if (isSelected) AccentActive else AccentInactive,
                cornerRadius = 20.dp,
                onClick = { onOptionSelected(option) }
            ) {
                Text(
                    text = option,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}
