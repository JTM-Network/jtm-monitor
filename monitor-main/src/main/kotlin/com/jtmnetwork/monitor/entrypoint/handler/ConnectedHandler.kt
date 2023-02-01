package com.jtmnetwork.monitor.entrypoint.handler

import com.google.inject.Inject
import com.jtmnetwork.monitor.core.domain.dto.PluginDTO
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.domain.model.Plugin
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import com.jtmnetwork.monitor.core.usecase.log.LogReporter
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import okhttp3.WebSocket
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Handles the initial handshake between server and client.
 */
class ConnectedHandler @Inject constructor(private val connection: MonitorConnection, private val logReporter: LogReporter, private val configuration: ServerConfiguration): EventHandlerImpl("connected_event") {

    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)

    /**
     * Using the server id sent by the server, persist it in the server configuration
     *
     * @param socket            the web socket
     * @param event             the event being sent.
     */
    override fun onEvent(socket: WebSocket, event: Event) {
        val info = getGson().fromJson(event.value, ServerInfo::class.java)
        configuration.setServerId(info.id)
        logger.info("Successfully connected to the server.")
//        val logs = logReporter.getLogs()
//        sendEvent(socket,"incoming_log_event", ServerLog(logs))
        val plugins: Map<String, Plugin> = fetchPlugins()
        sendEvent(socket,"update_plugins_event", PluginDTO(UUID.fromString(configuration.getServerId()), plugins))
        logger.info("Sent plugin update.")

//        logReporter.init()
    }

    fun fetchPlugins(): Map<String, Plugin> {
        val list: List<Plugin> = connection.framework.server.pluginManager.plugins.toList().stream()
            .map { pl -> Plugin(pl.name, pl.description.version, pl.isEnabled) }.toList()
        return list.associateBy { it.name }
    }
}