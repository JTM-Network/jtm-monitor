package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.data.service.OperationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/operation")
class OperationController @Autowired constructor(private val operationService: OperationService) {

    @GetMapping("/search/{query}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun search(@PathVariable query: String): Flux<ServerSentEvent<String>> {
        return operationService.search(query)
    }

    @GetMapping("/install/{server}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun installPlugin(@PathVariable server: String, @RequestParam("plugin_id") pluginId: String): Flux<ServerSentEvent<String>> {
        return Flux.empty()
    }
}