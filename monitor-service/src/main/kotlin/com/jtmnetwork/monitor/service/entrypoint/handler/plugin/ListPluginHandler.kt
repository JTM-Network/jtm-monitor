package com.jtmnetwork.monitor.service.entrypoint.handler.plugin

import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class ListPluginHandler @Autowired constructor(): EventHandlerImpl() {

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        TODO("Not yet implemented")
    }
}