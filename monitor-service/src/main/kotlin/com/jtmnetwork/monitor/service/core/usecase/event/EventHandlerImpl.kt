package com.jtmnetwork.monitor.service.core.usecase.event

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

abstract class EventHandlerImpl(private val name: String): EventHandler {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun getName(): String {
        return this.name
    }

    override fun sendEvent(session: WebSocketSession, value: Any): Mono<WebSocketMessage> {
        return message(session, value)
    }

    override fun sendEvent(session: WebSocketSession, name: String, value: Any): Mono<WebSocketMessage> {
        return message(session, name, value)
    }

    private fun message(session: WebSocketSession, value: Any): Mono<WebSocketMessage> {
        val event = Event(name, gson.toJson(value))
        return Mono.just(session.textMessage(gson.toJson(event)))
    }

    private fun message(session: WebSocketSession, name: String, value: Any): Mono<WebSocketMessage> {
        val event = Event(name, gson.toJson(value))
        return Mono.just(session.textMessage(gson.toJson(event)))
    }
}