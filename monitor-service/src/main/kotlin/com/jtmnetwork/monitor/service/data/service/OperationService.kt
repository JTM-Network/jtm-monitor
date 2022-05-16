package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.exception.FailedStartOperation
import com.jtmnetwork.monitor.service.core.usecase.spigot.ResourceSearchOperation
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OperationService @Autowired constructor(private val observer: OperationObserver) {

    fun search(query: String): Flux<ServerSentEvent<String>> {
        val sink = observer.addOperation(ResourceSearchOperation(query)) ?: return Flux.error(FailedStartOperation())
        return sink.asFlux().map { ServerSentEvent.builder(it).build() }
    }
}