package com.rafdev.moneyflow.ui.screens.overlay

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ExpenseFormOverlayViewModel @Inject constructor() : ViewModel() {

    var paymentMethod by mutableStateOf("CASH")

}