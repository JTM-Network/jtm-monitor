package com.jtmnetwork.monitor.module

import com.google.inject.AbstractModule
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.data.service.ServerInfoService
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandler
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.entrypoint.socket.MonitorListener

class MonitorModule(private val framework: Framework): AbstractModule() {
    override fun configure() {
        bind(Framework::class.java).toInstance(framework)
        bind(MonitorConnection::class.java)

        bind(ServerConfiguration::class.java)

        bind(ServerInfoService::class.java)
    }
}