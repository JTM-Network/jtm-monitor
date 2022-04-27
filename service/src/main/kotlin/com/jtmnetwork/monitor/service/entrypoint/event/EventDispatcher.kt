package com.jtmnetwork.monitor.service.entrypoint.event

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class EventDispatcher @Autowired constructor(private val eventRepository: EventRepository) {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun dispatch(session: WebSocketSession, str: String): Mono<WebSocketMessage> {
        val event = gson.fromJson(str, Event::class.java)
        val handler = eventRepository.getHandler(event.name) ?: return Mono.empty()
        return handler.onEvent(session, event)
    }
}