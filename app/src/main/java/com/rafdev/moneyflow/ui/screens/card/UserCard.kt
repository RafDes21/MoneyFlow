package com.rafdev.moneyflow.ui.screens.card

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.rafdev.moneyflow.R
import androidx.compose.foundation.lazy.grid.items
import com.rafdev.moneyflow.ui.components.CreditCard
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.navigation.screen.Screen
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

    var showColorDialog by remember { mutableStateOf(false) }
    var showCardTypeDialog by remember { mutableStateOf(false) }

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
                label = { Text("Título de la tarjeta") },
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
                label = { Text("Últimos 4 dígitos") },
                modifier = Modifier.width(170.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Color selector
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),
                    onClick = { showColorDialog = true },
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
                        Text("Color", color = Palette.TextColor, fontSize = 11.sp)
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
                    onClick = { showCardTypeDialog = true },
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
                        Text("Tipo de Tarjeta", color = Palette.TextColor, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(
                                id = if (cardType == 1) R.drawable.ic_visa else R.drawable.ic_master
                            ),
                            contentDescription = "Type Card"
                        )
                    }
                }
            }
        }

        if (showCardTypeDialog) {
            DialogApp(
                title = "Seleccionar tipo de tarjeta",
                description = "",
                onConfirm = {},
                onDismiss = { showCardTypeDialog = false },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_visa),
                            contentDescription = "Visa",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    viewModel.onCardTypeChange(1)
                                    showCardTypeDialog = false
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_master),
                            contentDescription = "MasterCard",
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    viewModel.onCardTypeChange(0)
                                    showCardTypeDialog = false
                                }
                        )
                    }
                }
            )
        }

        if (showColorDialog) {
            DialogApp(
                title = "Seleccionar color",
                description = "",
                onConfirm = {},
                onDismiss = { showColorDialog = false },
                content = {
                    val colorOptions = listOf(1, 2, 3, 4, 5, 6)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        items(colorOptions) { id ->
                            val color = when (id) {
                                1 -> Color(0xFF1E88E5)
                                2 -> Color(0xFFD32F2F)
                                3 -> Color(0xFF388E3C)
                                4 -> Color(0xFFFBC02D)
                                5 -> Color(0xFF6A1B9A)
                                6 -> Color(0xFF00897B)
                                else -> Color.Gray
                            }
                            Image(
                                painter = painterResource(id = R.drawable.ic_credit_card),
                                contentDescription = "credit card",
                                colorFilter = ColorFilter.tint(color),
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable {
                                        viewModel.onColorIdChange(id)
                                        showColorDialog = false
                                    }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserCardPreview() {
    UserCard()
}
