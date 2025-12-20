package com.rafdev.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun ActionTitleItem(
    title: String,
    @DrawableRes iconRes: Int,
    iconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    pushIconToEnd: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UIKitText(
            text = title,
            modifier = if (pushIconToEnd) Modifier.weight(1f) else Modifier
        )

        UIKitIcon(
            iconRes =iconRes,
            contentDescription = "Agregar",
            size = iconSize,
            padding = 4.dp,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionTitleItemPreview() {
    ActionTitleItem(
        title = "Gastos fijos",
        iconRes = R.drawable.ic_add,
        pushIconToEnd = false,
        onClick = {}
    )
}