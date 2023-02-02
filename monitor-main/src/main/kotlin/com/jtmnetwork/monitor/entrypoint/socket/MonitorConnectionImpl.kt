package com.jtmnetwork.monitor.entrypoint.socket

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.core.domain.model.Event
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class MonitorConnectionImpl: MonitorConnection {

    private val logger = LoggerFactory.getLogger(MonitorConnection::class.java)
    private val request = Request.Builder().url("ws://local.jtm-network.com:8787/monitor").build()
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var client = OkHttpClient.Builder().build()
    private lateinit var socket: WebSocket

    override fun disconnect() {
        socket.close(1001, "Connection has stopped.")
        logger.info("Connection has been closed.")
    }

    override fun sendEvent(name: String, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }

    fun setClient(client: OkHttpClient) {
        this.client = client
    }

    fun getClient(): OkHttpClient {
        return this.client
    }

    fun setSocket(socket: WebSocket) {
        this.socket = socket
    }

    fun getSocket(): WebSocket {
        return this.socket
    }

    fun getLogger(): Logger {
        return this.logger
    }

    fun getRequest(): Request {
        return this.request
    }
}