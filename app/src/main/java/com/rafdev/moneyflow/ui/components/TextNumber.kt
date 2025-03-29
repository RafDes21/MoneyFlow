package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextNumber(
    integer: String,
    separator: String,
    decimal: String,
    integerSize: TextUnit = 32.sp,
    separatorSize: TextUnit = 24.sp,
    decimalSize: TextUnit = 16.sp
) {
    Row {
        Text(
            text = integer,
            style = TextStyle(fontSize = integerSize)
        )
        Text(
            text = separator,
            style = TextStyle(fontSize = separatorSize),
            modifier = Modifier.align(Alignment.Bottom)
        )
        Text(
            text = decimal,
            style = TextStyle(fontSize = decimalSize),
            modifier = Modifier.align(Alignment.Top)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TextNumberPreview() {
    TextNumber(
        integer = "000",
        separator = ",",
        decimal = "00"
    )
}