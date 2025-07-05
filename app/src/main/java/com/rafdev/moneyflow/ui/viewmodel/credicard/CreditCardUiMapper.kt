package com.rafdev.moneyflow.ui.viewmodel.credicard

import com.rafdev.domain.model.CreditCardDomain

fun CreditCardUiModel.toDomain() = CreditCardDomain(
    total = totalFormatted,
    number = numberMasked,
    title = title,
    color = color,
    type = type
)