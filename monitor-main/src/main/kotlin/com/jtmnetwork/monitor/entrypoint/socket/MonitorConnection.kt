package com.jtmnetwork.monitor.entrypoint.socket

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.model.RetrySocketConnection
import com.jtmnetwork.monitor.core.domain.model.RetryThread
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
    private var connected = false
    private var tryingConnection = false

    val retry = RetrySocketConnection(this)
    val retryThread = RetryThread(this)
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun connect() {
        setTryingConnection(true)
        val request = Request.Builder().url("ws://local.jtm-network.com:8787/monitor").build()
        socket = client.newWebSocket(request, MonitorListener(framework, this, configuration, dispatcher))
        logger.info("Trying to connect to the server.")
        executor.submit(retryThread)
    }

    fun disconnect() {
        socket.close(1001, "Stopped connection.")
        logger.info("Connection has been closed.")
    }

    fun isConnected(): Boolean {
        return connected
    }

    fun setConnected(connected: Boolean) {
        this.connected = connected
    }

    fun isTryingToConnect(): Boolean {
        return this.tryingConnection
    }

    fun setTryingConnection(tryingConnection: Boolean) {
        this.tryingConnection = tryingConnection
    }
}