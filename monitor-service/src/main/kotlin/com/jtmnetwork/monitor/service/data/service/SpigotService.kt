package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SpigotService @Autowired constructor(private val observer: OperationObserver) {

    fun search(name: String): Flux<ServerSentEvent<String>> {
        return Flux.empty()
    }
}