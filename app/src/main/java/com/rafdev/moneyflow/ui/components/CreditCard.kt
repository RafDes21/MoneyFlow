package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.uikit.card.UIKitCard
import com.rafdev.moneyflow.ui.uikit.image.UIKitImage
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun CreditCard(
    title: String,
    total: String,
    number: String,
    backgroundColor: Color,
    cardTypeImageRes: Int,
) {
    UIKitCard(
        background = backgroundColor,
        onClick = {},
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UIKitText(
                    text = title.uppercase(),
                    fontSize = 18.sp
                )
                UIKitText(text = "$ $total", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                UIKitText(text = "XXXX  XXXX  XXX", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                UIKitText(text = number, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UIKitImage(
                    imageRes = R.drawable.ic_card_sim,
                    size = 30.dp,
                    contentDescription = "SIM"
                )

                UIKitImage(
                    imageRes = cardTypeImageRes,
                    size = 50.dp,
                    contentDescription = "Crédito"
                )
            }
        }
    }
}


@Preview
@Composable
fun CreditCardPreview() {
    CreditCard(
        title = "banco",
        total = "0.0",
        number = "1234",
        backgroundColor = CardColor,
        cardTypeImageRes = R.drawable.ic_visa
    )
}