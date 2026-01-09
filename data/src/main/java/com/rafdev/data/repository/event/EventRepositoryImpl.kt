package com.rafdev.data.repository.event

import com.rafdev.data.dao.event.EventDao
import com.rafdev.data.dao.event.EventExpenseDao
import com.rafdev.data.entities.event.EventEntity
import com.rafdev.data.entities.event.EventExpenseEntity
import com.rafdev.data.mapper.event.toDomain
import com.rafdev.domain.model.event.Event
import com.rafdev.domain.repository.event.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class EventRepositoryImpl(
    private val eventDao: EventDao,
    private val expenseDao: EventExpenseDao
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> =
        eventDao.getEvents().flatMapLatest { events ->
            combine(
                events.map { event ->
                    expenseDao.getExpenses(event.id)
                        .map { expenses ->
                            event.toDomain(expenses)
                        }
                }
            ) { it.toList() }
        }

    override suspend fun createEvent(title: String, budget: Double) {
        eventDao.upsert(
            EventEntity(
                id = 0,
                title = title,
                budget = budget,
                totalSpent = 0.0,
                createdAt = ""
            )
        )
    }

    override suspend fun addExpense(eventId: Int, name: String, amount: Double) {
        expenseDao.insert(
            EventExpenseEntity(
                id = 0,
                eventId = eventId,
                name = name,
                amount = amount,
                createdAt = ""
            )
        )

        val total = expenseDao.getTotal(eventId) ?: 0.0
        eventDao.updateTotal(eventId, total)
    }
}
