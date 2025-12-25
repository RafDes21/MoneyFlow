package com.rafdev.moneyflow.ui.uikit.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.theme.CardBorder
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIKitInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    isError: Boolean = false,
    enabled: Boolean = true
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

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        isError = isError,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        colors = textFieldColors
    )
}
