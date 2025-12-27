package com.rafdev.moneyflow.ui.screens.overlay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.TextSecondary
import com.rafdev.moneyflow.ui.uikit.button.UIKitButton

@Composable
fun EmptyCardsState(
    onAddCard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No tenés tarjetas guardadas",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        UIKitButton(
            onClick = onAddCard
        ) {
            Text("Agregar tarjeta")
        }
    }
}

@Preview
@Composable
fun EmptyCardsStatePreview(){
    EmptyCardsState {  }
}
