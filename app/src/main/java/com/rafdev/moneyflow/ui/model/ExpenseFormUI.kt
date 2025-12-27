package com.rafdev.moneyflow.ui.model

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class ExpenseFormUi(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val amount: String = ""
)

sealed class InputType {
    data object Text : InputType()
    data object Password : InputType()
    data object Amount : InputType()
    data object Email : InputType()
}

data class InputConfig(
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
)

@Composable
fun rememberInputConfig(
    type: InputType
): InputConfig {
    return when (type) {
        InputType.Text -> InputConfig(
            keyboardOptions = KeyboardOptions.Default,
            visualTransformation = VisualTransformation.None
        )

        InputType.Email -> InputConfig(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            visualTransformation = VisualTransformation.None
        )

        InputType.Password -> InputConfig(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        InputType.Amount -> InputConfig(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            visualTransformation = VisualTransformation.None
        )
    }
}

enum class IconPosition {
    LEFT, RIGHT
}

sealed class OverlayType {
    object None : OverlayType()
    object ExpenseForm : OverlayType()
    object CreditCardForm : OverlayType()
}

data class OverlayState(
    val visible: Boolean = false,
    val type: OverlayType = OverlayType.None,
    val origin: OverlayOrigin = OverlayOrigin.NONE
)

enum class OverlayOrigin {
    NONE,
    EXPENSE_FORM,
    PLANNED_EXPENSES
}