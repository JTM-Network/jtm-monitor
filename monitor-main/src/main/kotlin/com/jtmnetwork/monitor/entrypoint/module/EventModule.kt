package com.jtmnetwork.monitor.entrypoint.module

import com.google.inject.AbstractModule
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandler

class EventModule: AbstractModule() {
    override fun configure() {
        bind(EventDispatcher::class.java)
        bind(EventRepository::class.java)

        bind(ConnectedHandler::class.java)
    }
}