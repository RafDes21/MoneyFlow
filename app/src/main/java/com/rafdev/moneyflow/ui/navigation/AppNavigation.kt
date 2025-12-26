package com.rafdev.moneyflow.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
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
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.components.BottomSheet
import com.rafdev.moneyflow.ui.components.OverlayContainer
import com.rafdev.moneyflow.ui.components.bottombar.CustomBottomBar
import com.rafdev.moneyflow.ui.components.topbar.CustomTopBar
import com.rafdev.moneyflow.ui.model.ExpenseFormUi
import com.rafdev.moneyflow.ui.model.OverlayType
import com.rafdev.moneyflow.ui.screens.card.CreditCardForm
import com.rafdev.moneyflow.ui.screens.home.HomeScreen
import com.rafdev.moneyflow.ui.screens.note.CreditCardsScreen
import com.rafdev.moneyflow.ui.screens.overlay.ExpenseFormContent
import com.rafdev.moneyflow.ui.screens.planned.PlannedExpensesScreen
import com.rafdev.moneyflow.ui.screens.splash.SplashScreen
import com.rafdev.moneyflow.ui.viewmodel.GlobalFinanceViewModel
import com.rafdev.moneyflow.utils.getCurrentDateTime

@Composable
fun AppNavigation(
    globalVM: GlobalFinanceViewModel
) {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetMode by remember { mutableStateOf<SheetMode?>(null) }
    var showOverlay by remember { mutableStateOf(false) }

    var overlayType by remember {
        mutableStateOf<OverlayType>(OverlayType.None)
    }

    var currentForm by remember {
        mutableStateOf(ExpenseFormUi())
    }
    val showBars = currentRoute != Splash::class.qualifiedName


    val (title, showBackButton) = when (currentRoute) {
        Home::class.qualifiedName -> {
            stringResource(R.string.app_name) to false
        }

        FixedExpenses::class.qualifiedName -> {
            "Gastos programados" to true
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
                            currentForm = ExpenseFormUi()
                            showBottomSheet = true
                            sheetMode = mode
                        },
                        onOpenOverLay = {
                            overlayType = OverlayType.ExpenseForm
                            showOverlay = true
                        }
                    )
                }

                composable<Cards> {
                    CreditCardsScreen(
                        onAddCreditCard = {
                            showOverlay = true
                            overlayType = OverlayType.CreditCardForm
                        }
                    )
                }

                composable<FixedExpenses> {
                    PlannedExpensesScreen(
                        onAddExpense = {
                            sheetMode = SheetMode.ADD
                            currentForm = ExpenseFormUi()
                            showBottomSheet = true
                            overlayType = OverlayType.ExpenseForm

                        },
                        onEditExpense = { expense ->
                            sheetMode = SheetMode.EDIT
                            currentForm = ExpenseFormUi(
                                id = expense.id,
                                title = expense.name,
                                description = expense.description,
                                amount = expense.amount.toString()
                            )
                            showBottomSheet = true
                            overlayType = OverlayType.ExpenseForm
                        }
                    )
                }
            }

        }

        AnimatedVisibility(
            visible = showOverlay,
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it }
            )
        ) {
            OverlayContainer(
                onClose = { showOverlay = false }
            ) {
                when (overlayType) {
                    OverlayType.ExpenseForm -> {
                        ExpenseFormContent(onClose = {showOverlay = false})
                    }

                    OverlayType.CreditCardForm -> {
                        CreditCardForm(onClose = {showOverlay = false})
                    }

                    OverlayType.None -> Unit
                }
            }
        }

        if (showBottomSheet && sheetMode != null) {
            BottomSheet(
                mode = sheetMode!!,
                form = currentForm,
                onDismiss = { showBottomSheet = false },
                onSave = { form ->
                    globalVM.saveExpense(
                        id = form.id ?: 0,
                        title = form.title,
                        description = form.description,
                        currentDateTime = getCurrentDateTime(),
                        amount = form.amount.toDouble(),
                        typeValue = 1
                    )
                    showBottomSheet = false
                }
            )
        }
    }
}
