package com.rafdev.moneyflow.ui.screens.planned.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.icons.AppIcons
import com.rafdev.moneyflow.ui.screens.planned.UiMonth
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun MonthNavigator(
    month: UiMonth,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UIKitIcon(
            iconRes = AppIcons.Back,
            contentDescription = "Mes anterior",
            onClick = onPrevious
        )

        UIKitText(
            text = "${month.monthName} ${month.year}",
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        UIKitIcon(
            iconRes = AppIcons.Next,
            contentDescription = "Mes siguiente",
            onClick = onNext
        )
    }
}

@Preview(
    showBackground = true,
    name = "MonthNavigator Preview"
)
@Composable
fun MonthNavigatorPreview() {
    MonthNavigator(
        month = UiMonth(
            year = 2026,
            month = 1
        ),
        onPrevious = {},
        onNext = {},
        modifier = Modifier.padding(16.dp)
    )
}

