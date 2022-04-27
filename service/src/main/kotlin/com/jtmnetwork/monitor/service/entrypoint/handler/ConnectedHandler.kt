package com.jtmnetwork.monitor.service.entrypoint.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.model.ServerInfo
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.service.ServerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.*

@Component
class ConnectedHandler @Autowired constructor(private val serverService: ServerService): EventHandlerImpl() {

    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)
    private val gson = GsonBuilder().create()

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        val info = gson.fromJson(event.value, ServerInfo::class.java)
        logger.info("Client connected: ${session.id}")
        return if (info.id.isBlank())
            serverService.insert(Server(index = 0))
                .flatMap {
                    info.id = it.id.toString()
                    sendEvent(session, "connected_event", info)
                }
        else
            serverService.findById(UUID.fromString(info.id))
                .flatMap {
                    info.id = it.id.toString()
                    sendEvent(session, "connected_event", info)
                }
    }
}