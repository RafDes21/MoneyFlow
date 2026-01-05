package com.rafdev.moneyflow.ui.screens.overlay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.moneyflow.ui.theme.AccentActive
import com.rafdev.moneyflow.ui.theme.AccentInactive
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.CardFocused
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.theme.TextSecondary

@Composable
fun CardSelector(
    cards: List<CreditCardDomain>,
    selectedCardId: Int?,
    onCardSelected: (Int) -> Unit
) {
    Column {
        Text(
            text = "Seleccionar tarjeta",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        cards.forEach { card ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onCardSelected(card.id) },
                colors = CardDefaults.cardColors(
                    containerColor = if (card.id == selectedCardId)
                        AccentActive else AccentInactive
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${card.title} •••• ${card.number}",
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun CardSelectorPreview() {
    val previewCards = listOf(
        CreditCardDomain(
            id = 1,
            type = 1,
            title = "Visa Santander",
            number = "1234",
            total = 45000.0,
            color = 1
        ),
        CreditCardDomain(
            id = 2,
            type = 0,
            title = "Master BBVA",
            number = "9876",
            total = 120000.0,
            color = 2
        ),
        CreditCardDomain(
            id = 3,
            type = 1,
            title = "Visa Galicia",
            number = "4567",
            total = 8000.0,
            color = 3
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp)
    ) {
        CardSelector(
            cards = previewCards,
            selectedCardId = 2,
            onCardSelected = {}
        )
    }
}

