package com.jtmnetwork.monitor.service.entrypoint.socket

import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import com.jtmnetwork.monitor.service.entrypoint.event.EventDispatcher
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class MonitorSocketHandler @Autowired constructor(private val eventDispatcher: EventDispatcher, private val sessionService: SessionService): WebSocketHandler {

    private val logger = LoggerFactory.getLogger(MonitorSocketHandler::class.java)

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(session.receive()
            .flatMap { msg -> eventDispatcher.dispatch(session, msg.payloadAsText) }
            .doFinally {
                session.close()
                sessionService.deleteById(session.id)
                logger.info("Client disconnected: \nSession Id: ${session.id} \nSignal: ${it.name}")
            }
        )
    }
}