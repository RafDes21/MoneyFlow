package com.rafdev.moneyflow.ui.screens.form.expense.fix

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.model.ExpenseFormUi
import com.rafdev.moneyflow.ui.theme.CardBorder
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.Surface
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.TextSecondary
import com.rafdev.moneyflow.ui.uikit.button.UIKitButton
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormExpenseFix(
    viewModel: FormExpenseFixViewModel = hiltViewModel(),
    mode: SheetMode,
    form: ExpenseFormUi,
    onDismiss: () -> Unit,
) {
    LaunchedEffect(form) {
        viewModel.initForm(form)
    }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                FormExpenseEvent.Success -> {
                    onDismiss()
                }

                is FormExpenseEvent.Error -> {
                }
            }
        }
    }



    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Surface
    ) {
        ContentBottomSheet(
            mode = mode,
            inputTitle = state.title,
            description = state.description,
            amount = state.amount,
            isLoading = state.isLoading,
            isValid = state.isValid,
            onTitleChange = viewModel::onTitleChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onAmountChange = viewModel::onAmountChange,
            onSave = viewModel::saveExpense
        )
    }
}

@Composable
fun ContentBottomSheet(
    mode: SheetMode,
    inputTitle: String,
    description: String,
    amount: String,
    isLoading: Boolean,
    isValid: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onSave: () -> Unit
) {

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedBorderColor = Primary,
        unfocusedBorderColor = CardBorder,
        focusedLabelColor = Primary,
        unfocusedLabelColor = TextSecondary,
        cursorColor = Primary
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardColor)
            .padding(16.dp)
    ) {
        UIKitText(
            modifier = Modifier.fillMaxWidth(),
            color = TextPrimary,
            text = if (mode == SheetMode.ADD) "Nuevo gasto mensual"
            else "Actualizar gasto mensual",
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputTitle,
            onValueChange = onTitleChange,
            colors = textFieldColors,
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            colors = textFieldColors,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            colors = textFieldColors,
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UIKitButton(
            onClick = onSave,
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = TextPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                UIKitText(
                    if (mode == SheetMode.ADD) "Agregar" else "Actualizar"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentBottomSheetPreview() {
    ContentBottomSheet(
        mode = SheetMode.ADD,
        inputTitle = "",
        description = "",
        amount = "",
        isLoading = true,
        isValid = false,
        onTitleChange = {},
        onDescriptionChange = {},
        onAmountChange = {},
        onSave = {}
    )
}
