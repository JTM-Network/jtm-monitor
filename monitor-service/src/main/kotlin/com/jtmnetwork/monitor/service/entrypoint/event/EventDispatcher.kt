package com.jtmnetwork.monitor.service.entrypoint.event

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.repository.EventRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class EventDispatcher @Autowired constructor(private val eventRepository: EventRepository) {

    private val logger = LoggerFactory.getLogger(EventDispatcher::class.java)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun dispatch(session: WebSocketSession, str: String): Mono<WebSocketMessage> {
        try {
            logger.info("Received message.")
            val event = gson.fromJson(str, Event::class.java)
            val handler = eventRepository.getHandler(event.name) ?: return Mono.empty()
            logger.info("Found handler.")
            return handler.onEvent(session, event)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Mono.empty()
        }
    }
}