package com.jtmnetwork.monitor.service.core.usecase.event.impl

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.model.ServerInfo
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.service.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.*

@Component
class ConnectedHandler @Autowired constructor(private val serverService: ServerService): EventHandlerImpl() {

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        val info = event.value as ServerInfo
        return if (info.id.isBlank())
            serverService.insert(Server(index = 0))
                .flatMap { sendEvent(session, "connected_event", it) }
        else
            serverService.findById(UUID.fromString(info.id))
                .flatMap { sendEvent(session, "connected_event", it) }
    }
}