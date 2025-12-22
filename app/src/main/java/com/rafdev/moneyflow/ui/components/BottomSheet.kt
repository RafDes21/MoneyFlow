package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.theme.CardBorder
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.PrimaryVariant
import com.rafdev.moneyflow.ui.theme.Surface
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.TextSecondary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText
import com.rafdev.moneyflow.utils.getCurrentDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    mode: SheetMode,
    title: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit
) {
    var inputTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Surface
    ) {
        ContentBottomSheet(
            mode = mode,
            headerTitle = title,
            inputTitle = inputTitle,
            description = description,
            amount = amount,
            onTitleChange = { inputTitle = it },
            onDescriptionChange = { description = it },
            onAmountChange = { amount = it },
            onSave = {
                val currentDateTime = getCurrentDateTime()
                onSave(inputTitle, description, amount, currentDateTime)
            }
        )
    }
}

@Composable
fun ContentBottomSheet(
    mode: SheetMode,
    headerTitle: String,
    inputTitle: String,
    description: String,
    amount: String,
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

    val buttonColor =
        if (mode == SheetMode.ADD) Primary
        else PrimaryVariant

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

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = TextPrimary
            ),
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentBottomSheetPreview() {
    ContentBottomSheet(
        mode = SheetMode.ADD,
        headerTitle = "Agregar gasto",
        inputTitle = "",
        description = "",
        amount = "",
        onTitleChange = {},
        onDescriptionChange = {},
        onAmountChange = {},
        onSave = {}
    )
}
