package com.rafdev.moneyflow.ui.screens.card

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserCardViewModel @Inject constructor() : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> = _number.asStateFlow()

    private val _cardType = MutableStateFlow(0)
    val cardType: StateFlow<Int> = _cardType.asStateFlow()

    private val _colorId = MutableStateFlow(1)
    val colorId: StateFlow<Int> = _colorId.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onNumberChange(newNumber: String) {
        _number.value = newNumber
    }

    fun onCardTypeChange(newType: Int) {
        _cardType.value = newType
    }

    fun onColorIdChange(newColor: Int) {
        _colorId.value = newColor
    }

}