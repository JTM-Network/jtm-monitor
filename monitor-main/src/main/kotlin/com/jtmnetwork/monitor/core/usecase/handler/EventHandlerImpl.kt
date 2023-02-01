package com.jtmnetwork.monitor.core.usecase.handler

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.core.domain.model.Event
import okhttp3.WebSocket

abstract class EventHandlerImpl(private val name: String): EventHandler {

    private val gson = GsonBuilder().serializeNulls().create()

    override fun getName(): String {
        return this.name
    }

    override fun getGson(): Gson {
        return this.gson
    }

    override fun sendEvent(socket: WebSocket, name: String, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }

    override fun sendEvent(socket: WebSocket, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }
}