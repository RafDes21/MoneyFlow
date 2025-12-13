package com.rafdev.moneyflow.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.ui.components.BottomSheet
import com.rafdev.moneyflow.ui.components.bottombar.CustomBottomBar
import com.rafdev.moneyflow.ui.components.topbar.CustomTopBar
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.NoteScreen
import com.rafdev.moneyflow.ui.screens.splash.SplashScreen
import com.rafdev.moneyflow.ui.viewmodel.GlobalFinanceViewModel

@Composable
fun AppNavigation(
    globalVM: GlobalFinanceViewModel
) {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var showBottomSheet by remember { mutableStateOf(false) }

    val showBars = currentRoute != Splash::class.qualifiedName

    Scaffold(
        topBar = {
            if (showBars) {
                CustomTopBar(
                    showBackButton = false,
                    onBackClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            if (showBars) {
                CustomBottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Splash,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Splash> {
                SplashScreen {
                    navController.navigate(Home) {
                        popUpTo(Splash) { inclusive = true }
                    }
                }
            }

            composable<Home> {
                HomeScreen(
                    onNavigate = {},
                    activeBottomSheet = {
                        showBottomSheet = true
                    }
                )
            }

            composable<Cards> {
                NoteScreen(
                    onAddClick = {
                    }
                )
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                title = "Agregar monto fijo mensual",
                onDismiss = { showBottomSheet = false }
            ) { inputTitle, description, amount, currentDaTime ->
                globalVM.saveExpense(inputTitle, description, currentDaTime, amount.toDouble(), 1)
                showBottomSheet = false
            }
        }
    }
}
