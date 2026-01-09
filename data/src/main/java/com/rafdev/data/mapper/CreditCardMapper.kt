package com.rafdev.data.mapper

import com.rafdev.data.entities.CreditCardEntity
import com.rafdev.domain.model.CreditCardDomain

fun CreditCardEntity.toDomain(): CreditCardDomain {
    return CreditCardDomain(
        id = this.id,
        title = this.title,
        number = this.number,
        type = type,
        color = color,
        total = total
    )
}

fun CreditCardDomain.toEntity(): CreditCardEntity {
    return CreditCardEntity(
        title = this.title,
        number = this.number,
        type = type,
        color = color,
        total = total
    )
}