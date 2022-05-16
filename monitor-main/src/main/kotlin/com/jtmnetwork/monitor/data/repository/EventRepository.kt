package com.jtmnetwork.monitor.data.repository

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.core.usecase.repository.InMemoryRepository
import com.jtmnetwork.monitor.core.usecase.handler.EventHandler
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandler

@Singleton
class EventRepository: InMemoryRepository<EventHandler, String>() {

    fun registerHandler(name: String, handler: EventHandler) {
        insert(name, handler)
    }

    fun getHandler(name: String): EventHandler? {
        return findById(name)
    }
}