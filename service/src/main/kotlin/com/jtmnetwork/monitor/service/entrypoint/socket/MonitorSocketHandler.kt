package com.jtmnetwork.monitor.service.entrypoint.socket

import com.jtmnetwork.monitor.service.entrypoint.event.EventDispatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class MonitorSocketHandler @Autowired constructor(val eventDispatcher: EventDispatcher): WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(session.receive()
            .flatMap { msg -> eventDispatcher.dispatch(session, msg.payloadAsText) }
            .doFinally {
                session.close()

            }
        )
    }
}