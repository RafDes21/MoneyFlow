package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.R

@Composable
fun CreditCard(
    title: String,
    total: String,
    number: String,
    cardType: Int,
    colorId: Int
) {
    val backgroundColor = when (colorId) {
        1 -> Color(0xFF1E88E5)
        2 -> Color(0xFFD32F2F)
        3 -> Color(0xFF388E3C)
        4 -> Color(0xFFFBC02D)
        5 -> Color(0xFF6A1B9A)
        6 -> Color(0xFF00897B)
        else -> Color.Gray
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor)

    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.ic_card_sim),
                    contentDescription = "sim"
                )
                Column {
                    Text(text = title, fontSize = 18.sp)
                    Text(text = "$ $total", fontSize = 14.sp)
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "XXXX  XXXX  XXX", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = number, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            val imageRes = if (cardType == 1) {
                R.drawable.ic_visa
            } else {
                R.drawable.ic_master
            }
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .size(50.dp),
                painter = painterResource(id = imageRes), contentDescription = "Card Brand"
            )
        }
    }

}

@Preview
@Composable
fun CreditCardPreview() {
    CreditCard(
        title = "title",
        total = "0.0",
        number = "1234",
        cardType = 1,
        colorId = 1
    )
}