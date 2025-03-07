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
import com.rafdev.moneyflow.ui.navigation.screen.Screen
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.NoteScreen
import com.rafdev.moneyflow.ui.screens.planned.PlannedExpensesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomNavScreens = Screen.bottomNavScreens.map { it.route }

    Scaffold(
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
                        Screen.Notas -> NoteScreen()
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = Screen.bottomNavScreens
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    screen.icon?.let {
                        Icon(imageVector = it, contentDescription = screen.title)
                    }
                },
                label = {
                    screen.title?.let {
                        Text(it)
                    }
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}