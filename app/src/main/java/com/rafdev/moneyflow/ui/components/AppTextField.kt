package com.rafdev.moneyflow.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rafdev.moneyflow.ui.model.InputType
import com.rafdev.moneyflow.ui.model.rememberInputConfig
import com.rafdev.moneyflow.ui.theme.CardBorder
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.TextSecondary

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    type: InputType = InputType.Text,
    label: String
) {

    val config = rememberInputConfig(type)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedBorderColor = Primary,
        unfocusedBorderColor = CardBorder,
        focusedLabelColor = Primary,
        unfocusedLabelColor = TextSecondary,
        cursorColor = Primary
    )

    OutlinedTextField(
        modifier= modifier,
        value = value,
        label = { Text(label) },
        colors = textFieldColors,
        onValueChange = onValueChange,
        keyboardOptions = config.keyboardOptions,
        visualTransformation = config.visualTransformation
    )

}

@Preview
@Composable
fun AppTextFieldPreview() {
    AppTextField(
        value = "first",
        onValueChange = {},
        label = "first"
    )
}