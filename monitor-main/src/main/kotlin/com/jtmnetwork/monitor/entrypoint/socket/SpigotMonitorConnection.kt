package com.jtmnetwork.monitor.entrypoint.socket

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher

@Singleton
class SpigotMonitorConnection @Inject constructor(val framework: Framework, private val configuration: ServerConfiguration, private val dispatcher: EventDispatcher): MonitorConnectionImpl() {

    override fun connect() {
        setSocket(getClient().newWebSocket(getRequest(), SpigotMonitorListener(framework, configuration, dispatcher)))
        getLogger().info("Trying to connect to the server.")
    }
}