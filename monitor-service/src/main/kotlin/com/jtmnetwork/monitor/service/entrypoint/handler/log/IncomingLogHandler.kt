package com.jtmnetwork.monitor.service.entrypoint.handler.log

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.ServerLog
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class IncomingLogHandler @Autowired constructor(private val logRepository: LogRepository): EventHandlerImpl() {

    private val logger = LoggerFactory.getLogger(IncomingLogHandler::class.java)
    private val gson = GsonBuilder().create()

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        val log = gson.fromJson(event.value, ServerLog::class.java)
        log.message.forEach { logRepository.sendMessage(session.id, it) }
        return Mono.empty()
    }
}