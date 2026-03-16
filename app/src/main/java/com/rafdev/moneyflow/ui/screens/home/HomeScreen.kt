package com.rafdev.moneyflow.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.domain.model.Expense
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.SheetMode
import com.rafdev.moneyflow.ui.components.ActionTitleItem
import com.rafdev.moneyflow.ui.components.CreditCard
import com.rafdev.moneyflow.ui.components.DialogApp
import com.rafdev.moneyflow.ui.components.ExpenseDetailBottomSheet
import com.rafdev.moneyflow.ui.components.SalaryProgressCard
import com.rafdev.moneyflow.ui.icons.AppIcons
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.screens.creditcardform.AddEditCardViewModel
import com.rafdev.moneyflow.ui.screens.home.components.SwipeCreditCardItem
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.BodyText
import com.rafdev.moneyflow.ui.theme.CardColor
import com.rafdev.moneyflow.ui.theme.SubtitleText
import com.rafdev.moneyflow.ui.theme.TitleText
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
    onOpenSheet: (SheetMode) -> Unit,
    onOpenOverLay: () -> Unit,
    onAddSalary: () -> Unit,
    onAddCreditCard: () -> Unit,
    onUpdateCreditCard: (CreditCardDomain) -> Unit

) {

    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val creditCards by viewModel.uiState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialogApp by remember { mutableStateOf(false) }
    var expenseItem by remember { mutableStateOf<Expense?>(null) }
    var creditCard by remember { mutableStateOf<CreditCardDomain?>(null) }


    val salary by viewModel.salary.collectAsState()

    val icon = salary?.let {
        if (it.isHidden) AppIcons.EyeHide else AppIcons.EyeShow
    } ?: AppIcons.Add

    val showAmount = salary?.let {
        if (!it.isHidden) it.amount.toString() else "*******"
    } ?: "Sin Saldo"

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
                    style = TitleText,
                    text = stringResource(R.string.salary_section_title)
                )
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ActionTitleItem(
                        title = showAmount,
                        iconRes = icon,
                        iconPosition = IconPosition.START,
                        onClick = {
                            if (salary == null) onAddSalary()
                            else viewModel.toggleHideSalary()
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

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    SalaryProgressCard(
                        salary = salary?.amount ?: 0.0,
                        expenses = totalExpenses
                    )
                }

                ActionTitleItem(
                    title = "Tarjetas",
                    iconRes = R.drawable.ic_add,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    onClick = {
                        onAddCreditCard()
                    }
                )

            }

            when {

                creditCards.data.isEmpty() -> {
                    item(key = "cards_empty") {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(top = 50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            UIKitText(
                                text = "Empieza agregando tu primera tarjeta",
                                style = BodyText,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                else -> {
                    items(items = creditCards.data, key = { it.id }) { card ->
                        CreditCardListItem(
                            card = card,
                            onDelete = { cardToDelete ->
                                creditCard = cardToDelete
                                showDialogApp = true

                            },
                            onUpdate = {
                                onUpdateCreditCard(card)
                            }

                        )
                    }
                }
            }

        }

        if (showDialogApp) {
            DialogApp(
                title = "Eliminar",
                description = "¿Estás seguro de que deseas eliminar este elemento? Esta acción no se puede deshacer.",
                onConfirm = {
                    creditCard?.let {
                        viewModel.deleteCreditCardById(it.id)
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

@Composable
fun CreditCardListItem(
    card: CreditCardDomain,
    onDelete: (CreditCardDomain) -> Unit,
    onUpdate: (CreditCardDomain) -> Unit
) {
    val cardType = if (card.type == 1) R.drawable.ic_visa else R.drawable.ic_master
    val cardColor = when (card.color) {
        1 -> Color(0xFF1E88E5)
        2 -> Color(0xFFD32F2F)
        3 -> Color(0xFF388E3C)
        4 -> Color(0xFFFBC02D)
        5 -> Color(0xFF6A1B9A)
        6 -> Color(0xFF00897B)
        else -> CardColor
    }

    SwipeCreditCardItem(
        onEdit = { onUpdate(card)},
        onDelete = { onDelete(card) }
    ) {
        CreditCard(
            title = card.title,
            total = card.total.toString(),
            number = card.number,
            backgroundColor = cardColor,
            cardTypeImageRes = cardType
        )
    }
}



