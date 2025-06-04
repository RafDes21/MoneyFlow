package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.theme.Palette


@Composable
fun EditTextCustom(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                color = Palette.CardTextColor
            )
        },
        textStyle = TextStyle(
            color = Palette.CardTextColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp)
    )

}

@Preview(showSystemUi = true)
@Composable
fun EditTextCustomPreview() {
    EditTextCustom(
        query = "",
        onQueryChange = {},
        placeholder = "Escribe algo...",
    )
}