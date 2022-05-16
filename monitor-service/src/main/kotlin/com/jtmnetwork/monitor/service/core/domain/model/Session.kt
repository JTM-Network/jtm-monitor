package com.jtmnetwork.monitor.service.core.domain.model

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

data class Session(val id: String, val socketSession: WebSocketSession) {
    private val gson = GsonBuilder().create()

    fun sendEvent(name: String, value: Any): Mono<Void> {
        return message(name, value)
    }

    private fun message(name: String, value: Any): Mono<Void> {
        val event = Event(name, gson.toJson(value))
        return socketSession.send(Mono.just(socketSession.textMessage(gson.toJson(event))))
    }
}
