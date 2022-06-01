package com.jtmnetwork.monitor.service.core.usecase.event

import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

interface EventHandler {
    fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage>

    fun sendEvent(session: WebSocketSession, value: Any): Mono<WebSocketMessage>

    fun getName(): String
}