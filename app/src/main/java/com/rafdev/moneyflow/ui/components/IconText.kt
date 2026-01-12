package com.rafdev.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.icons.AppIcons
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun IconText(
    text: String,
    @DrawableRes iconRes: Int,
    iconPosition: IconPosition = IconPosition.LEFT,
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconPosition == IconPosition.LEFT) {
            UIKitIcon(
                iconRes = iconRes,
                contentDescription = text,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(spacing))
        }

        UIKitText(text = text)

        if (iconPosition == IconPosition.RIGHT) {
            Spacer(modifier = Modifier.width(spacing))
            UIKitIcon(
                iconRes = iconRes,
                contentDescription = text,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextPreviewLeft() {
    IconText(
        text = "Monthly expenses",
        iconRes = AppIcons.Add,
        iconPosition = IconPosition.LEFT,
        onClick = {}
    )
}
