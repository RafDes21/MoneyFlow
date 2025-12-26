package com.rafdev.moneyflow.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.ActionButton
import com.rafdev.moneyflow.ui.components.CreditCard
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun CreditCardsScreen(
    viewModel: CardsViewModel = hiltViewModel(),
    onAddCreditCard: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        ActionButton(
            text = "Agregar Tarjeta",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .padding(10.dp),
            onClick = {onAddCreditCard()}
        )

        when {
            state.loading -> {
                CircularProgressIndicator()
            }

            state.error.isNotEmpty() -> {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "Error: ${state.error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }

            state.data.isEmpty() -> {
                UIKitText(
                    text = "Empieza agregando tu primera tarjeta",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
            }

            else -> {
                LazyColumn {
                    items(state.data) { card ->
                        val cardType = if (card.type == 1) {
                            R.drawable.ic_visa
                        } else {
                            R.drawable.ic_master
                        }
                        val cardColor = when (card.color) {
                            1 -> Color(0xFF1E88E5)
                            2 -> Color(0xFFD32F2F)
                            3 -> Color(0xFF388E3C)
                            4 -> Color(0xFFFBC02D)
                            5 -> Color(0xFF6A1B9A)
                            6 -> Color(0xFF00897B)
                            else -> CardColor
                        }

                        CreditCard(
                            title = card.title,
                            total = card.total.toString(),
                            number = card.number,
                            backgroundColor = cardColor,
                            cardTypeImageRes = cardType
                        )
                    }
                }
            }
        }

    }
}
