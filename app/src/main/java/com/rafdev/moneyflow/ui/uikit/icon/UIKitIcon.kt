package com.rafdev.moneyflow.ui.uikit.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.theme.Primary

@Composable
fun UIKitIcon(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    padding: Dp = 0.dp,
    tint: Color = Primary,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {

    val clickableModifier = if (onClick != null) {
        Modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
    } else {
        Modifier
    }
    Box(
        modifier = modifier
            .padding(padding)
            .then(clickableModifier)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(size),
            tint = tint
        )
    }
}

@Preview
@Composable
fun UIKitIconPreview() {
    UIKitIcon(
        iconRes = UIKitIcons.Add,
        "Add",
        padding = 8.dp
    )
}