package com.rafdev.moneyflow.ui.screens.overlay


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.domain.model.CreditCardDomain
import com.rafdev.moneyflow.ui.screens.overlay.components.CardSelector
import com.rafdev.moneyflow.ui.screens.overlay.components.EmptyCardsState
import com.rafdev.moneyflow.ui.screens.overlay.components.PaymentMethodSelector
import com.rafdev.moneyflow.ui.uikit.button.UIKitButton
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.input.UIKitInput
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun ExpenseFormOverlay(
    viewModel: ExpenseFormOverlayViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onAddCard: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    ExpenseFormContent(
        state = state,
        paymentMethod = viewModel.paymentMethod,
        selectedCardId = viewModel.selectedCreditCardId,
        onPaymentMethodChange = viewModel::onPaymentMethodChange,
        onCardSelected = viewModel::onCreditCardSelected,
        onSave = viewModel::saveForm,
        onClose = onClose,
        onOpenAddCard=onAddCard
    )
}

@Composable
fun ExpenseFormContent(
    state: UiState,
    paymentMethod: String,
    selectedCardId: Int?,
    onPaymentMethodChange: (String) -> Unit,
    onCardSelected: (Int) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
    onOpenAddCard: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                UIKitIcon(
                    iconRes = UIKitIcons.close,
                    contentDescription = "Cerrar",
                    onClick = onClose
                )

                UIKitText(text = "Agregar / Editar gasto")
            }

            Divider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                contentPadding = PaddingValues(16.dp)
            ) {

                item {
                    UIKitInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = { title = it },
                        label = "Título"
                    )
                }

                item {
                    Spacer(Modifier.height(12.dp))
                    UIKitInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = { description = it },
                        label = "Descripción"
                    )
                }

                item {
                    Spacer(Modifier.height(12.dp))
                    UIKitInput(
                        value = amount,
                        onValueChange = { amount = it },
                        label = "Monto",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                item {
                    Spacer(Modifier.height(12.dp))
                    PaymentMethodSelector(
                        selected = paymentMethod,
                        onSelected = onPaymentMethodChange
                    )
                }

                if (paymentMethod == "CREDIT") {
                    item {
                        Spacer(Modifier.height(18.dp))

                        if (state.data.isEmpty()) {
                            EmptyCardsState(onAddCard =  onOpenAddCard)
                        } else {
                            CardSelector(
                                cards = state.data,
                                selectedCardId = selectedCardId,
                                onCardSelected = onCardSelected
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    UIKitButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onSave
                    ) {
                        UIKitText("Guardar")
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ExpenseFormPreview() {
    ExpenseFormContent(
        state = UiState(
            data = listOf(
                CreditCardDomain(1, 1, "Visa Santander", "1234", 50000.0, 1),
                CreditCardDomain(2, 0, "Master BBVA", "9876", 80000.0, 2)
            )
        ),
        paymentMethod = "CREDIT",
        selectedCardId = 1,
        onPaymentMethodChange = {},
        onCardSelected = {},
        onSave = {},
        onClose = {},
        onOpenAddCard = {}
    )
}