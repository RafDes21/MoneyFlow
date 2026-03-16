package com.rafdev.moneyflow.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeCreditCardItem(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {

    val cardShape = RoundedCornerShape(20.dp)

    val dismissState = rememberDismissState(
        confirmValueChange = { value ->
            when (value) {
                DismissValue.DismissedToStart -> {
                    onDelete()
                    false
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
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            .clip(cardShape),
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