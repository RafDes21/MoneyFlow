package com.rafdev.domain.usecase.event

import com.rafdev.domain.repository.event.EventRepository
import javax.inject.Inject

class AddEventExpenseUC @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(
        eventId: Int,
        name: String,
        amount: Double
    ) {
        require(name.isNotBlank()) { "El gasto debe tener nombre" }
        require(amount > 0) { "El monto debe ser mayor a 0" }

        repository.addExpense(
            eventId = eventId,
            name = name,
            amount = amount
        )
    }
}
