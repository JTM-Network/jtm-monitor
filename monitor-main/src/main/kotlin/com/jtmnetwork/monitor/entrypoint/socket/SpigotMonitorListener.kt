package com.jtmnetwork.monitor.entrypoint.socket

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.model.serverinfo.SpigotServerInfo
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.Response
import okhttp3.WebSocket

class SpigotMonitorListener(framework: Framework, private val configuration: ServerConfiguration, private val dispatcher: EventDispatcher): MonitorListenerImpl(dispatcher) {

    private val server = framework.server

    override fun onOpen(webSocket: WebSocket, response: Response) {
        dispatcher.sendEvent(webSocket, "spigot_connected_event", SpigotServerInfo(configuration, server))
    }
}