package com.jtmnetwork.monitor.entrypoint.event

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

@Singleton
class EventDispatcher @Inject constructor(private val repository: EventRepository) {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun dispatch(socket: WebSocket, msg: String) {
        val event = gson.fromJson(msg, Event::class.java)
        val handler = repository.getHandler(event.name)
        handler.ifPresent { h -> h.onEvent(socket, event) }
    }

    fun sendEvent(socket: WebSocket, name: String, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }
}