package com.rafdev.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun ActionTitleItem(
    title: String,
    @DrawableRes iconRes: Int,
    iconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    iconPosition: IconPosition = IconPosition.END,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.clickable(enabled = onClick != null) {
            onClick?.invoke()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconPosition == IconPosition.START) {
            UIKitIcon(
                iconRes = iconRes,
                contentDescription = title,
                size = iconSize,
            )
            Spacer(Modifier.width(8.dp))
        }

        UIKitText(
            text = title,
        )

        if (iconPosition == IconPosition.END) {
            Spacer(Modifier.width(8.dp))
            UIKitIcon(
                iconRes = iconRes,
                contentDescription = title,
                size = iconSize,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionTitleItemPreview() {
    ActionTitleItem(
        title = "Gastos fijos",
        iconRes = R.drawable.ic_add,
        onClick = {}
    )
}