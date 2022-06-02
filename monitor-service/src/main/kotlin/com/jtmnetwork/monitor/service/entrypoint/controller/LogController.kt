package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.model.Console
import com.jtmnetwork.monitor.service.data.service.plugin.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@RestController
@RequestMapping("/logs")
class LogController @Autowired constructor(private val logService: LogService) {

    /**
     * Get console log stream using id.
     *
     * @param id                    the id of the websocket session.
     * @return                      the Flux of the Sink.Many
     * @see                         ServerSentEvent<String>
     */
    @GetMapping("/{id}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getConsoleLogs(@PathVariable id: String): Flux<ServerSentEvent<String>> {
        return logService.getConsoleLogs(id)
    }


    @GetMapping("/all")
    fun getConsoles(): Mono<Array<Console>> {
        return logService.getConsoles().collect(Collectors.toList()).map { it.toTypedArray() }
    }
}