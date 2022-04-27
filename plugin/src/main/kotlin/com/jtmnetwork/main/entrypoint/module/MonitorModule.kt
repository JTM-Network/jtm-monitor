package com.jtmnetwork.main.entrypoint.module

import com.google.inject.AbstractModule
import com.jtm.framework.Framework
import com.jtmnetwork.main.data.repository.EventRepository
import com.jtmnetwork.main.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.main.entrypoint.event.EventDispatcher
import com.jtmnetwork.main.entrypoint.handler.ConnectedHandler
import com.jtmnetwork.main.entrypoint.socket.MonitorConnection
import com.jtmnetwork.main.entrypoint.socket.MonitorListener

class MonitorModule(private val framework: Framework): AbstractModule() {


    override fun configure() {
        bind(Framework::class.java).toInstance(framework)
        bind(EventDispatcher::class.java)
        bind(EventRepository::class.java)

        bind(MonitorListener::class.java)
        bind(MonitorConnection::class.java)

        bind(ServerConfiguration::class.java)

        bind(ConnectedHandler::class.java)
    }
}