package com.rafdev.moneyflow.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.ui.components.CreditCard

@Composable
fun NoteScreen(
    viewModel: CardsViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Agregar tarjeta") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> {
                    CircularProgressIndicator()
                }

                state.error.isNotEmpty() -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }

                state.data.isEmpty() -> {
                    Text(
                        text = "Empieza agregando tu primera tarjeta",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    LazyColumn {
                        items(state.data) { card ->
                            CreditCard(
                                title = card.title,
                                total = card.total.toString(),
                                number = card.number,
                                cardType = card.type,
                                colorId = card.color
                            )
                        }
                    }
                }
            }
        }
    }
}
