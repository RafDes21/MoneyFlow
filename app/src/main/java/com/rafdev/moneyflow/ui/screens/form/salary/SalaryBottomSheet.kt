package com.rafdev.moneyflow.ui.screens.form.salary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafdev.moneyflow.ui.components.AppBottomSheet
import com.rafdev.moneyflow.ui.screens.form.salary.model.SalarySheetMode
import com.rafdev.moneyflow.ui.uikit.button.UIKitButton
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun SalaryBottomSheet(
    mode: SalarySheetMode,
    salaryId: Int? = null,
    onDismiss: () -> Unit,
    viewModel: SalaryViewModel = hiltViewModel()
) {
    LaunchedEffect(salaryId) {
        viewModel.init(mode, salaryId)
    }

    val state by viewModel.uiState.collectAsState()

    val title = if (mode == SalarySheetMode.CREATE) {
        "Nuevo salario"
    } else {
        "Actualizar salario"
    }

    AppBottomSheet(
        title = title,
        onDismiss = onDismiss,
        content = {
            SalaryFormContent(
                state = state,
                onCompanyChange = viewModel::onCompanyChange,
                onAmountChange = viewModel::onAmountChange
            )
        },
        actions = {
            SalaryActions(
                mode = mode,
                isValid = state.isValid,
                isLoading = state.isLoading,
                onSave = {
                    if (mode == SalarySheetMode.CREATE) {
                        viewModel.createSalary()
                    } else {
                        viewModel.updateSalary()
                    }
                }
            )
        }
    )
}


@Composable
fun SalaryFormContent(
    state: SalaryUiState,
    onCompanyChange: (String) -> Unit,
    onAmountChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = state.companyName,
            onValueChange = onCompanyChange,
            label = { Text("Empresa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.amount,
            onValueChange = onAmountChange,
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun SalaryActions(
    mode: SalarySheetMode,
    isValid: Boolean,
    isLoading: Boolean,
    onSave: () -> Unit
) {
    UIKitButton(
        onClick = onSave,
        enabled = isValid,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            UIKitText(
                if (mode == SalarySheetMode.CREATE) "Agregar" else "Actualizar"
            )
        }
    }
}
