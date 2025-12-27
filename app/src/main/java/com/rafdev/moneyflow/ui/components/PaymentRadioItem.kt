package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.AccentActive
import com.rafdev.moneyflow.ui.theme.AccentInactive
import com.rafdev.moneyflow.ui.theme.TextPrimary

@Composable
fun PaymentRadioItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
    ) {

        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (selected) AccentActive else AccentInactive,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AccentActive)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = label,
            color = TextPrimary,
            fontSize = 13.sp
        )
    }
}

@Preview
@Composable
fun PaymentRadioItemPreview() {
    PaymentRadioItem(
        label = "item",
        selected = true,
        onClick = {}
    )
}