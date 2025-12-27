package com.rafdev.moneyflow.ui.uikit.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.TextPrimary

@Composable
fun UIKitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    cornerRadius: Dp = 16.dp,
    background: Color = Primary,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = TextPrimary,
            disabledContainerColor = Primary.copy(alpha = 0.4f),
            disabledContentColor = TextPrimary.copy(alpha = 0.6f)
        )
    ) {
        content()

    }
}
