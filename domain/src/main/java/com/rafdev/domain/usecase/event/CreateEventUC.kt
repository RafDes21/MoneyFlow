package com.rafdev.domain.usecase.event

import com.rafdev.domain.repository.event.EventRepository
import javax.inject.Inject

class CreateEventUC @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(
        title: String,
        budget: Double
    ) {
        require(title.isNotBlank()) { "El título no puede estar vacío" }
        require(budget > 0) { "El presupuesto debe ser mayor a 0" }

        repository.createEvent(
            title = title,
            budget = budget
        )
    }
}