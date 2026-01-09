package com.rafdev.domain.repository.event

import com.rafdev.domain.model.event.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    suspend fun createEvent(title: String, budget: Double)
    suspend fun addExpense(eventId: Int, name: String, amount: Double)
}
