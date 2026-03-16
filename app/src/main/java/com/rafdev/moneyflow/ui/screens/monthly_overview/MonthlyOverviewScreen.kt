package com.rafdev.moneyflow.ui.screens.monthly_overview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.components.FullScreenLoading
import com.rafdev.moneyflow.ui.components.FullScreenMessage
import com.rafdev.moneyflow.ui.components.SalaryProgressCard
import com.rafdev.moneyflow.ui.icons.AppIcons
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.screens.monthly_overview.components.ExpenseList
import com.rafdev.moneyflow.ui.screens.monthly_overview.components.MonthNavigator
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.text.UIKitText


@Composable
fun MonthlyOverviewScreen(
    viewModel: MonthlyOverviewViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onAddExpense: () -> Unit,
    onEditExpense: (Int) -> Unit,
    onAddSalary: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val salaryState by viewModel.salary.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val totalSalary by viewModel.totalSalary.collectAsState()

    val salaryNotLoaded by viewModel.salaryNotLoaded.collectAsState()
    var isHidden by remember { mutableStateOf(false) }

    val icon = when {
        salaryNotLoaded -> AppIcons.Add
        isHidden -> AppIcons.EyeHide
        else -> AppIcons.EyeShow
    }

    PlannedExpensesContent(
        state = state,
        icon = icon,
        isHidden = isHidden,
        salaryNotLoaded = salaryNotLoaded,
        salary = salaryState,
        totalSalary = totalSalary,
        totalExpenses = totalExpenses,
        month = month,
        onBackPressed = onBackPressed,
        onAddExpense = onAddExpense,
        onEditExpense = onEditExpense,
        onDeleteExpense = viewModel::deleteExpenseById,
        previous = viewModel::previousMonth,
        nextMonth = viewModel::nextMonth,
        onAddSalary = onAddSalary,
        onToggleHidden = { isHidden = !isHidden }

    )
}


@Composable
fun PlannedExpensesContent(
    state: ExpenseState,
    salary: String,
    month: UiMonth,
    onBackPressed: () -> Unit,
    onAddExpense: () -> Unit,
    onEditExpense: (Int) -> Unit,
    onDeleteExpense: (Int, Int?) -> Unit,
    previous: () -> Unit,
    nextMonth: () -> Unit,
    onAddSalary: () -> Unit,
    totalSalary: Double,
    totalExpenses: Double,
    icon: Int,
    isHidden: Boolean,
    onToggleHidden: () -> Unit,
    salaryNotLoaded: Boolean
) {

    var showDialogApp by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {

        UIKitIcon(
            iconRes = AppIcons.BackScreen,
            contentDescription = "Volver",
            onClick = { onBackPressed() }
        )
        Spacer(Modifier.height(30.dp))

        MonthNavigator(
            month = month,
            onPrevious = { previous() },
            onNext = { nextMonth() },
        )
        Spacer(Modifier.height(20.dp))
        UIKitText(
            text = stringResource(R.string.salary_section_title)
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ActionTitleItem(
                title = if (!isHidden) salary else "*******",
                iconRes = icon,
                iconPosition = IconPosition.START
            ) {
                if (salaryNotLoaded) {
                    onAddSalary()
                } else {
                    onToggleHidden()
                }
            }
            ActionTitleItem(
                title = "Gasto",
                iconRes = UIKitIcons.Add,
                onClick = onAddExpense,
            )
        }
        Spacer(Modifier.height(20.dp))

        if (!salaryNotLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SalaryProgressCard(
                    salary = totalSalary,
                    expenses = totalExpenses
                )
            }
        }


        Box(Modifier.fillMaxSize()) {
            Log.d("probando", "state $state")
            when {
                state.isLoading -> FullScreenLoading(size = 40.dp)

                state.error.isNotEmpty() -> FullScreenMessage(text = state.error)
                state.success.isEmpty() -> {
                    FullScreenMessage(
                        text = stringResource(R.string.no_expenses_this_month)
                    )
                }

                else -> ExpenseList(
                    state.success,
                    onEditExpense = { id ->
                        onEditExpense(id)
                    },
                    onDeleteExpense = { expense ->
                        expenseToDelete = expense
                        showDialogApp = true
                    }
                )
            }
        }

        if (showDialogApp) {
            DialogApp(
                title = "Eliminar",
                description = "¿Estás seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.",
                onConfirm = {
                    onDeleteExpense(expenseToDelete?.id!!, expenseToDelete?.creditCardId)
                    expenseToDelete = null
                    showDialogApp = false
                },
                onDismiss = {
                    expenseToDelete = null
                    showDialogApp = false
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    name = "Planned Expenses Preview"
)
@Composable
fun PlannedExpensesContentPreview() {
    PlannedExpensesContent(
        state = ExpenseState(
            success = listOf(
                Expense(
                    id = 1,
                    name = "Alquiler",
                    amount = 120000.0,
                    type = "Fijo",
                    description = "Departamento",
                    image = "",
                    color = "#4CAF50",
                    date = "2026-01-01",
                    category = "Vivienda",
                    recurring = true,
                    period = "Mensual",
                    paymentMethod = "Transferencia",
                    notes = "",
                    isPaid = false,
                    creditCardId = null
                ),
                Expense(
                    id = 2,
                    name = "Internet",
                    amount = 15000.0,
                    type = "Servicio",
                    description = "Fibra óptica",
                    image = "",
                    color = "#2196F3",
                    date = "2026-01-05",
                    category = "Servicios",
                    recurring = true,
                    period = "Mensual",
                    paymentMethod = "Débito",
                    notes = "Vence el 10",
                    isPaid = true,
                    creditCardId = null
                )
            )
        ),
        salary = "",
        month = UiMonth(
            month = 1,
            year = 2026
        ),
        onBackPressed = {},
        onAddExpense = {},
        onEditExpense = {},
        onDeleteExpense = { _, _ -> },
        previous = {},
        nextMonth = {},
        onAddSalary = {},
        totalSalary = 0.0,
        totalExpenses = 0.0,
        onToggleHidden = {},
        isHidden = true,
        salaryNotLoaded = false,
        icon = AppIcons.Add
    )
}



