package com.rafdev.moneyflow.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.components.OverlayContainer
import com.rafdev.moneyflow.ui.components.bottombar.CustomBottomBar
import com.rafdev.moneyflow.ui.components.topbar.CustomTopBar
import com.rafdev.moneyflow.ui.model.OverlayOrigin
import com.rafdev.moneyflow.ui.model.OverlayState
import com.rafdev.moneyflow.ui.model.OverlayType
import com.rafdev.moneyflow.ui.screens.card.CreditCardForm
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.creditcard.CreditCardsScreen
import com.rafdev.moneyflow.ui.screens.events.EventsScreen
import com.rafdev.moneyflow.ui.screens.form.expense.fix.FormExpenseFix
import com.rafdev.moneyflow.ui.screens.form.salary.SalaryBottomSheet
import com.rafdev.moneyflow.ui.screens.form.salary.model.SalarySheetMode
import com.rafdev.moneyflow.ui.screens.monthly_overview.MonthlyOverviewScreen
import com.rafdev.moneyflow.ui.screens.overlay.ExpenseFormOverlay
import com.rafdev.moneyflow.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetMode by remember { mutableStateOf(SheetMode.ADD) }

    var showSalarySheet by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(SalarySheetMode.CREATE) }

    var overlayState by remember {
        mutableStateOf(OverlayState())
    }

    var expenseId by remember {
        mutableStateOf<Int?>(null)
    }

    val showBars = currentRoute != Splash::class.qualifiedName &&
            currentRoute != FixedExpenses::class.qualifiedName


    val (title, showBackButton) = when (currentRoute) {
        Home::class.qualifiedName -> {
            stringResource(R.string.app_name) to false
        }

        else -> {
            stringResource(R.string.app_name) to false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                if (showBars) {
                    CustomTopBar(
                        title,
                        showBackButton = showBackButton,
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
                        onNavigate = {
                            navController.navigate(FixedExpenses)
                        },
                        onOpenSheet = { mode ->
                            expenseId = null
                            showBottomSheet = true
                            sheetMode = mode
                        },
                        onOpenOverLay = {
                            overlayState = OverlayState(
                                visible = true,
                                type = OverlayType.ExpenseForm,
                                origin = OverlayOrigin.NONE
                            )
                        },
                        onAddSalary = {
                            mode = SalarySheetMode.CREATE
                            showSalarySheet = true
                        }
                    )
                }

                composable<Cards> {
                    CreditCardsScreen(
                        onAddCreditCard = {
                            overlayState = OverlayState(
                                visible = true,
                                type = OverlayType.CreditCardForm,
                                origin = OverlayOrigin.NONE
                            )
                        }
                    )
                }

                composable<FixedExpenses> {
                    MonthlyOverviewScreen(
                        onBackPressed = { navController.popBackStack() },
                        onAddExpense = {
                            sheetMode = SheetMode.ADD
                            expenseId = null
                            showBottomSheet = true

                        },
                        onEditExpense = { id ->
                            sheetMode = SheetMode.EDIT
                            expenseId = id
                            showBottomSheet = true
                        },
                        onAddSalary = {
                            mode = SalarySheetMode.CREATE
                            showSalarySheet = true
                        }
                    )
                }
                composable<Events> {
                    EventsScreen()
                }
            }

        }

        AnimatedVisibility(
            visible = overlayState.visible,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            OverlayContainer(
                onClose = { overlayState = OverlayState() }
            ) {
                when (overlayState.type) {
                    OverlayType.ExpenseForm -> {
                        ExpenseFormOverlay(
                            onClose = {
                                overlayState = OverlayState()
                            },
                            onAddCard = {
                                overlayState = OverlayState(
                                    visible = true,
                                    type = OverlayType.CreditCardForm,
                                    origin = OverlayOrigin.EXPENSE_FORM
                                )
                            }
                        )
                    }

                    OverlayType.CreditCardForm -> {
                        CreditCardForm(
                            onClose = {
                                overlayState = when (overlayState.origin) {
                                    OverlayOrigin.EXPENSE_FORM ->
                                        OverlayState(
                                            visible = true,
                                            type = OverlayType.ExpenseForm
                                        )

                                    else -> OverlayState()
                                }
                            }
                        )
                    }

                    OverlayType.None -> Unit
                }
            }
        }

    }

    if (showBottomSheet) {
        FormExpenseFix(
            mode = sheetMode,
            expenseId = expenseId,
            onDismiss = { showBottomSheet = false },
        )
    }

    if (showSalarySheet) {
        SalaryBottomSheet(
            mode = mode,
            onDismiss = { showSalarySheet = false }
        )
    }

}
