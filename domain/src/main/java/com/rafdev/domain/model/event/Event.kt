package com.rafdev.domain.model.event

data class Event(
    val id: Int,
    val title: String,
    val budget: Double,
    val totalSpent: Double,
    val expenses: List<EventExpense>
) {
    val progress: Double
        get() = (totalSpent / budget).coerceIn(0.0, 1.0)
}

data class EventExpense(
    val id: Int,
    val name: String,
    val amount: Double
)