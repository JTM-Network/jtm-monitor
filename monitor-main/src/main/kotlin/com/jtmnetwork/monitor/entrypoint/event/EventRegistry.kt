package com.jtmnetwork.monitor.entrypoint.event

import com.google.inject.Injector
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.handler.CommandHandler
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandler

class EventRegistry(private val repository: EventRepository, private val injector: Injector) {

    fun init() {
        repository.registerHandler("connected_event", injector.getInstance(ConnectedHandler::class.java))
        repository.registerHandler("command_event", injector.getInstance(CommandHandler::class.java))
    }
}