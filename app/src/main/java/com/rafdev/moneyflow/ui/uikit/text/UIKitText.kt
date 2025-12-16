package com.rafdev.moneyflow.ui.uikit.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.TextPrimary

@Composable
fun UIKitText(
    text: String,
    fontSize: TextUnit = 16.sp,
    modifier: Modifier = Modifier,
    color: Color = Color(TextPrimary),
    textAlign: TextAlign = TextAlign.Start
) {

    Text(
        text = text,
        fontSize = fontSize,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = TextStyle(
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )

}