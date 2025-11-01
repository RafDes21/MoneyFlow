package com.rafdev.moneyflow.ui.navigation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.ui.components.BottomSheet
import com.rafdev.moneyflow.ui.navigation.components.BottomNavigationBar
import com.rafdev.moneyflow.ui.navigation.components.TopBar
import com.rafdev.moneyflow.ui.navigation.screen.Screen
import com.rafdev.moneyflow.ui.screens.card.UserCard
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.NoteScreen
import com.rafdev.moneyflow.ui.screens.planned.PlannedExpensesScreen
import com.rafdev.moneyflow.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomNavScreens = Screen.bottomNavScreens.map { it.route }

    val showBackButton = currentRoute == Screen.UserCard.route

    var showBottomSheet by remember { mutableStateOf(false) }

    val view = LocalView.current
    val context = LocalContext.current
    val activity = context as Activity
    val window = activity.window

    val windowInsetsController = remember {
        WindowInsetsControllerCompat(window, view)
    }

    // Ocultar o mostrar barras del sistema según la ruta actual
    DisposableEffect(currentRoute) {
        if (currentRoute == Screen.Splash.route) {
            windowInsetsController.hide(
                WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
            )
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            windowInsetsController.show(
                WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
            )
        }

        onDispose { /* no-op */ }
    }

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
                            },
                            activeBottomSheet = {showBottomSheet = true}
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

        if (showBottomSheet) {
            BottomSheet(onDismiss = { showBottomSheet = false })
        }
    }
}
