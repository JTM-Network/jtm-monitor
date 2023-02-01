package com.jtmnetwork.monitor.entrypoint.event

import com.google.inject.Injector
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.handler.CommandHandler
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandler
import com.jtmnetwork.monitor.entrypoint.handler.UpdateResponseHandler

class EventRegistry(private val repository: EventRepository, private val injector: Injector) {

    fun init() {
        repository.registerHandler(injector.getInstance(ConnectedHandler::class.java))
        repository.registerHandler(injector.getInstance(CommandHandler::class.java))
        repository.registerHandler(injector.getInstance(UpdateResponseHandler::class.java))
    }
}