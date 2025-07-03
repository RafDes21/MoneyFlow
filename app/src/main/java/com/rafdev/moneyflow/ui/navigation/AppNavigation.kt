package com.rafdev.moneyflow.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.ui.navigation.components.BottomNavigationBar
import com.rafdev.moneyflow.ui.navigation.components.TopBar
import com.rafdev.moneyflow.ui.navigation.screen.Screen
import com.rafdev.moneyflow.ui.screens.card.UserCard
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.NoteScreen
import com.rafdev.moneyflow.ui.screens.planned.PlannedExpensesScreen
import com.rafdev.moneyflow.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation(onSplashFinished: () -> Unit) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomNavScreens = Screen.bottomNavScreens.map { it.route }

    val showBackButton = currentRoute == Screen.UserCard.route

    Scaffold(
        topBar = {
            if (currentRoute in bottomNavScreens || currentRoute == Screen.UserCard.route) {
                TopBar(
                    showBackButton = showBackButton,
                    onBackClick = { navController.popBackStack() }
                )
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
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen {
                    onSplashFinished()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }

            Screen.bottomNavScreens.forEach { screen ->
                composable(screen.route) {
                    when (screen) {
                        Screen.Home -> HomeScreen(
                            onNavigate = {
                                navController.navigate(Screen.PlannedExpensesScreen.route)
                            }
                        )

                        Screen.Cards -> NoteScreen(
                            onAddClick = {
                                navController.navigate(Screen.UserCard.route)
                            }
                        )

                        else -> {}
                    }
                }
            }

            composable(Screen.PlannedExpensesScreen.route) {
                PlannedExpensesScreen()
            }
            composable(Screen.UserCard.route) {
                UserCard()
            }
        }
    }
}
