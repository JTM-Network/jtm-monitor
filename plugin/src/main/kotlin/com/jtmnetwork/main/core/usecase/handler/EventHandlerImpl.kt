package com.jtmnetwork.main.core.usecase.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.main.core.domain.model.Event
import okhttp3.WebSocket

abstract class EventHandlerImpl: EventHandler {

    private val gson = GsonBuilder().serializeNulls().create()

    override fun sendEvent(socket: WebSocket, name: String, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }
}