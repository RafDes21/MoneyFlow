package com.rafdev.moneyflow.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.ActionButton
import com.rafdev.moneyflow.ui.components.CreditCard
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardsScreen(
    viewModel: CardsViewModel = hiltViewModel(),
    onAddCreditCard: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // 🔴 Estado para confirmar eliminación
    var showDeleteDialog by remember { mutableStateOf(false) }
    var cardToDelete by remember { mutableStateOf<CreditCardDomain?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ➕ Botón agregar
        ActionButton(
            text = "Agregar Tarjeta",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .padding(10.dp),
            onClick = { onAddCreditCard() }
        )

        when {
            state.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error.isNotEmpty() -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
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
                    items(
                        items = state.data,
                        key = { it.id }
                    ) { card ->

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

                        SwipeCreditCardItem(
                            onEdit = {
                                //viewModel.onEditCard(card)
                            },
                            onDelete = {
                                cardToDelete = card
                                showDeleteDialog = true
                            }
                        ) {
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

        // 🔔 Diálogo de confirmación
        if (showDeleteDialog && cardToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    cardToDelete = null
                },
                title = {
                    Text("Eliminar tarjeta")
                },
                text = {
                    Text("¿Seguro que querés eliminar esta tarjeta?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteCreditCardById(cardToDelete?.id ?: 0)
                            showDeleteDialog = false
                            cardToDelete = null
                        }
                    ) {
                        Text("Eliminar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            cardToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeCreditCardItem(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmValueChange = { value ->
            when (value) {
                DismissValue.DismissedToStart -> {
                    onDelete()
                    false // ⛔ no desaparece hasta confirmar
                }
                DismissValue.DismissedToEnd -> {
                    onEdit()
                    false
                }
                else -> false
            }
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            SwipeBackground(dismissState)
        },
        dismissContent = {
            content()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val (color, icon, alignment) = when (direction) {
        DismissDirection.StartToEnd -> Triple(
            Color(0xFF22C55E),
            Icons.Default.Edit,
            Alignment.CenterStart
        )
        DismissDirection.EndToStart -> Triple(
            Color.Red,
            Icons.Default.Delete,
            Alignment.CenterEnd
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

