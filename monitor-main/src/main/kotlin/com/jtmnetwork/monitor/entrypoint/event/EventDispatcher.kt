package com.jtmnetwork.monitor.entrypoint.event

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.data.repository.EventRepository
import okhttp3.WebSocket

@Singleton
class EventDispatcher @Inject constructor(private val repository: EventRepository) {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    /**
     * Converts a message from the websocket to an event, and retrieves
     * the handler for the event and process the event.
     *
     * @param socket        the websocket.
     * @param msg           the string based message.
     */
    fun dispatch(socket: WebSocket, msg: String) {
        val event = gson.fromJson(msg, Event::class.java)
        val handler = repository.getHandler(event.name)
        handler.ifPresent { h -> h.onEvent(socket, event) }
    }

    /**
     * Send an event message to the server in json format.
     *
     * @param socket        the websocket.
     * @param name          the event name.
     * @param value         the event value.
     */
    fun sendEvent(socket: WebSocket, name: String, value: Any) {
        val event = Event(name, gson.toJson(value))
        val json = gson.toJson(event)
        socket.send(json)
    }
}