package com.rafdev.moneyflow.ui.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector? = null, val title: String? = null) {
    data object Home : Screen("Home", Icons.Default.Home, "Home")
    data object Notas : Screen("notas", Icons.Default.List, "Notas")
    data object PlannedExpensesScreen : Screen("detailExpense")

    companion object {
        val bottomNavScreens = listOf(Home, Notas)
    }
}
