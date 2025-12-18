package com.rafdev.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun ActionTitleItem(
    title: String,
    @DrawableRes iconRes: Int,
    iconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        UIKitText(
            text = title,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = Primary,
            modifier = Modifier.size(iconSize)
        )
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