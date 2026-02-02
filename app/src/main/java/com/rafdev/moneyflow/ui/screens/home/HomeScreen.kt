package com.rafdev.moneyflow.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.components.ActionButton
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.components.ExpenseCard
import com.rafdev.moneyflow.ui.components.ExpenseDetailBottomSheet
import com.rafdev.moneyflow.ui.components.IconText
import com.rafdev.moneyflow.ui.components.SalaryProgressCard
import com.rafdev.moneyflow.ui.icons.AppIcons
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.uikit.card.UIKitCard
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.text.UIKitText
import com.rafdev.moneyflow.utils.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
    onOpenSheet: (SheetMode) -> Unit,
    onOpenOverLay: () -> Unit,
    onAddSalary: () -> Unit
) {

    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val totalSalary by viewModel.totalSalary.collectAsState()


    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialogApp by remember { mutableStateOf(false) }
    var expenseItem by remember { mutableStateOf<Expense?>(null) }

    val state by viewModel.state.collectAsState()
    val salaryState by viewModel.salary.collectAsState()
    val salaryNotLoaded by viewModel.salaryNotLoaded.collectAsState()
    var isHidden by remember { mutableStateOf(false) }

    val icon = when {
        salaryNotLoaded -> AppIcons.Add
        isHidden -> AppIcons.EyeHide
        else -> AppIcons.EyeShow
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(10.dp, 20.dp, 10.dp, 0.dp)

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)

        ) {
            item {
                UIKitText(
                    text = stringResource(R.string.salary_section_title)
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ActionTitleItem(
                        title = if (!isHidden) salaryState else "*******",
                        iconRes = icon,
                        iconPosition = IconPosition.START,
                        onClick = {
                            if (salaryNotLoaded) {
                                onAddSalary()
                            } else {
                                isHidden = !isHidden
                            }
                        }
                    )

                    ActionTitleItem(
                        title = "Gasto",
                        iconRes = UIKitIcons.Add,
                        onClick = { onOpenSheet(SheetMode.ADD) },
                    )
                }
                Spacer(Modifier.height(10.dp))

                ActionTitleItem(
                    modifier = Modifier.padding(bottom = 8.dp),
                    title = stringResource(R.string.view_month_detail),
                    iconRes = AppIcons.OpenScreen,
                    onClick = { onNavigate() }
                )

                if (!salaryNotLoaded) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        SalaryProgressCard(
                            salary = totalSalary,
                            expenses = totalExpenses
                        )
                    }
                }


                ActionTitleItem(
                    title = "Tarjetas",
                    iconRes = R.drawable.ic_add,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    onClick = { }
                )

            }

            state.success?.let { expenses ->
                items(expenses, key = { it.id }) { expense ->
                    if (expense.type == "recurring") {
                        val dateTimeParts = expense.date.split(" ")
                        val date = dateTimeParts.getOrNull(0) ?: ""
                        val time = dateTimeParts.getOrNull(1) ?: ""

                        ExpenseCard(
                            title = expense.name,
                            time = time,
                            amount = expense.amount.toString(),
                            date = date,
                            onDetail = {
                                showBottomSheet = true
                                expenseItem = expense
                            },
                            onUpdate = { /* Acción para actualizar */ },
                            onDelete = {
                                showDialogApp = true
                                expenseItem = expense
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }


        }

        if (showDialogApp) {
            DialogApp(
                title = "Eliminar",
                description = "¿Estás seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.",
                onConfirm = {
                    expenseItem?.id?.let {
                        viewModel.deleteExpenseById(it, expenseItem?.creditCardId)
                    }
                    showDialogApp = false
                },
                onDismiss = { showDialogApp = false }
            )
        }

        if (showBottomSheet) {
            expenseItem?.let {
                ExpenseDetailBottomSheet(
                    expense = it
                ) {
                    showBottomSheet = false
                }
            }

        }
    }

}



