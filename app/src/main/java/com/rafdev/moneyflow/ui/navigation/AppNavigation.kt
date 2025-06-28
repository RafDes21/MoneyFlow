package com.rafdev.moneyflow.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.ui.navigation.components.BottomNavigationBar
import com.rafdev.moneyflow.ui.navigation.components.TopBar
import com.rafdev.moneyflow.ui.navigation.screen.Screen
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.NoteScreen
import com.rafdev.moneyflow.ui.screens.planned.PlannedExpensesScreen
import com.rafdev.moneyflow.ui.theme.Palette

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomNavScreens = Screen.bottomNavScreens.map { it.route }

    Scaffold(
        topBar = {
            if (currentRoute in bottomNavScreens) {
                TopBar()
            }
        },
        bottomBar = {
            if (currentRoute in bottomNavScreens) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            Screen.bottomNavScreens.forEach { screen ->
                composable(screen.route) {
                    when (screen) {
                        Screen.Home -> HomeScreen(
                            onNavigate = {
                                navController.navigate(Screen.PlannedExpensesScreen.route)
                            }
                        )
                        Screen.Cards -> NoteScreen(){}
                        else -> {}
                    }
                }
            }

            composable(Screen.PlannedExpensesScreen.route) {
                PlannedExpensesScreen()
            }
        }
    }
}
