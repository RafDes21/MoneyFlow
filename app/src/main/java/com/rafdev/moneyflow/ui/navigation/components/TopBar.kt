package com.rafdev.moneyflow.ui.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = "MoneyFlow") },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        })
}