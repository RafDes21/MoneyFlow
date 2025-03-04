package com.rafdev.moneyflow.ui.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("Home", Icons.Default.Home, "Home")
    object Notas : Screen("notas", Icons.Default.List, "Notas")
}
