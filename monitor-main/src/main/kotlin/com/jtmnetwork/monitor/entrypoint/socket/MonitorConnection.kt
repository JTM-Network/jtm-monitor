package com.jtmnetwork.monitor.entrypoint.socket

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.LoggerFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Singleton
class MonitorConnection @Inject constructor(private val framework: Framework, private val configuration: ServerConfiguration, private val dispatcher: EventDispatcher) {

    private val logger = LoggerFactory.getLogger(MonitorConnection::class.java)
    private val client = OkHttpClient.Builder().build()

    private lateinit var socket: WebSocket

    fun connect() {
        val request = Request.Builder().url("ws://local.jtm-network.com:8787/monitor").build()
        socket = client.newWebSocket(request, MonitorListener(framework, configuration, dispatcher))
        logger.info("Trying to connect to the server.")
    }

    fun disconnect() {
        socket.close(1001, "Stopped connection.")
        logger.info("Connection has been closed.")
    }
}