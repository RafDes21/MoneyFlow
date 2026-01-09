package com.rafdev.domain.usecase.event

import com.rafdev.domain.model.event.Event
import com.rafdev.domain.repository.event.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsUC @Inject constructor(private val repository: EventRepository) {

    operator fun invoke(): Flow<List<Event>> = repository.getEvents()

}