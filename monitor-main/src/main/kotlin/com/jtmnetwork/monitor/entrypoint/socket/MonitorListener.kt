package com.jtmnetwork.monitor.entrypoint.socket

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.slf4j.LoggerFactory

@Singleton
class MonitorListener @Inject constructor(private val framework: Framework, private val configuration: ServerConfiguration, private val dispatcher: EventDispatcher): WebSocketListener() {

    private val logger = LoggerFactory.getLogger(MonitorListener::class.java)

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logger.info("Closed connection.")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        logger.info("Connection failed: ${t.message}")
        t.printStackTrace()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        logger.info("Received message.")
        dispatcher.dispatch(webSocket, text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        dispatcher.sendEvent(webSocket, "connected_event", ServerInfo(configuration, framework.server))
        logger.info("Opened connection.")
    }
}