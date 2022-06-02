package com.jtmnetwork.monitor.service.entrypoint.handler.download

import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class DownloadCompleteHandler @Autowired constructor(private val observer: OperationObserver): EventHandlerImpl("plugin_download_complete_event") {

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        TODO("Not yet implemented")
    }
}