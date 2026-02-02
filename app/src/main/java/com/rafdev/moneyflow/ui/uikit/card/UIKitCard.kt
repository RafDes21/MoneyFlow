package com.rafdev.moneyflow.ui.uikit.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.CardBorder
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.TextMuted
import com.rafdev.moneyflow.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIKitCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    background: Color = CardColor,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = background
        ),
        border = BorderStroke(1.dp, CardBorder),
        shape = RoundedCornerShape(16.dp),
        onClick = {onClick?.invoke()}
    ) {
        content()
    }
}

@Preview(
    name = "UIKitCard – Dark",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun UIKitCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        UIKitCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Gastos Totales",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
                Text(
                    text = "$ 125.430",
                    color = TextSecondary,
                    fontSize = 22.sp
                )

                Text(
                    text = "Actualizado hoy",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }
    }
}
