package com.jtmnetwork.monitor.entrypoint.socket

import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.slf4j.LoggerFactory

abstract class MonitorListenerImpl(private val dispatcher: EventDispatcher): WebSocketListener() {

    private val logger = LoggerFactory.getLogger(MonitorListenerImpl::class.java)

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logger.info("Connection has been closed.")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        logger.info("Retrying connection in 5 seconds: ${t.message}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        dispatcher.dispatch(webSocket, text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }
}