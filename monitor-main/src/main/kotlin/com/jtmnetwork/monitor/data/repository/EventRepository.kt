package com.jtmnetwork.monitor.data.repository

import com.google.inject.Singleton
import com.jtm.framework.core.usecase.repository.InMemoryRepository
import com.jtmnetwork.monitor.core.usecase.handler.EventHandler
import java.util.*

@Singleton
class EventRepository: InMemoryRepository<EventHandler, String>() {

    fun registerHandler(handler: EventHandler) {
        insert(handler.getName(), handler)
    }

    fun getHandler(name: String): Optional<EventHandler> {
        return findById(name)
    }
}