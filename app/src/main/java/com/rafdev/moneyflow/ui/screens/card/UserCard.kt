package com.rafdev.moneyflow.ui.screens.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.CreditCard
import com.rafdev.moneyflow.ui.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(viewModel: UserCardViewModel = hiltViewModel()) {

    val title by viewModel.title.collectAsState()
    val number by viewModel.number.collectAsState()
    val cardType by viewModel.cardType.collectAsState()
    val colorId by viewModel.colorId.collectAsState()

    val iconCardColor = when (colorId) {
        1 -> Color(0xFF1E88E5)
        2 -> Color(0xFFD32F2F)
        3 -> Color(0xFF388E3C)
        4 -> Color(0xFFFBC02D)
        5 -> Color(0xFF6A1B9A)
        6 -> Color(0xFF00897B)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            CreditCard(
                title = title,
                total = "",
                number = number,
                cardType = cardType,
                colorId = colorId
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Título de la tajeta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = number,
                onValueChange = {
                    if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                        viewModel.onNumberChange(it)
                    }
                },
                label = { Text(text = "Últimos 4 dígitos") },
                modifier = Modifier.width(170.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Palette.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Color", color = Palette.TextColor, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_credit_card),
                            contentDescription = "credit card",
                            colorFilter = ColorFilter.tint(iconCardColor),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Palette.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Tipo de Tarjeta", color = Palette.TextColor, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            modifier = Modifier
                                .size(30.dp),
                            painter =
                            painterResource(
                                id = if (cardType == 1) R.drawable.ic_visa else R.drawable.ic_master
                            ),
                            contentDescription = " Type Card"
                        )
                    }
                }

            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserCardPreview() {
    UserCard()
}