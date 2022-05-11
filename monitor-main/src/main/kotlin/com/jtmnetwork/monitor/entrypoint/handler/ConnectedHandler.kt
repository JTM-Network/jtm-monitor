package com.jtmnetwork.monitor.entrypoint.handler

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.domain.model.ServerLog
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import com.jtmnetwork.monitor.core.usecase.log.LogReporter
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

/**
 * Handles the initial handshake between server and client.
 */
class ConnectedHandler @Inject constructor(private val connection: MonitorConnection, private val logReporter: LogReporter, private val configuration: ServerConfiguration): EventHandlerImpl() {

    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)
    private val gson = GsonBuilder().create()

    /**
     * Using the server id sent by the server, persist it in the server configuration
     *
     * @param socket        the web socket
     * @param event         the event being sent.
     */
    override fun onEvent(socket: WebSocket, event: Event) {
        val info = gson.fromJson(event.value, ServerInfo::class.java)
        configuration.setServerId(info.id)
        logger.info("Successfully connected to the server.")
        val logs = logReporter.getLogs()
        connection.sendEvent("incoming_log_event", ServerLog(logs))
    }
}