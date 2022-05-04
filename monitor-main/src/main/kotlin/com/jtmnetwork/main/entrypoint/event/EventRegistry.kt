package com.jtmnetwork.main.entrypoint.event

import com.google.inject.Injector
import com.jtmnetwork.main.data.repository.EventRepository
import com.jtmnetwork.main.entrypoint.handler.ConnectedHandler

class EventRegistry(private val repository: EventRepository, private val injector: Injector) {

    fun init() {
        repository.registerHandler("connected_event", injector.getInstance(ConnectedHandler::class.java))
    }
}