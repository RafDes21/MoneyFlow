package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.Palette

@Composable
fun TextNumber(
    integer: String,
    separator: String,
    decimal: String,
    integerSize: TextUnit = 32.sp,
    separatorSize: TextUnit = 24.sp,
    decimalSize: TextUnit = 16.sp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center

) {
    Row(
        modifier = Modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =  "$",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        Text(
            text = integer,
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(fontSize = integerSize)
        )
        Text(
            text = separator,
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(fontSize = separatorSize),
            modifier = Modifier.align(Alignment.Bottom)
        )
        Text(
            text = decimal,
            color = MaterialTheme.colorScheme.onBackground,
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