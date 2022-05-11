package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.data.service.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController("/logs")
class LogController @Autowired constructor(private val logService: LogService) {

    @GetMapping("/{id}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getConsoleLogs(@PathVariable id: String): Flux<ServerSentEvent<String>> {
        return logService.getConsoleLogs(id)
    }

    @GetMapping("/all")
    fun getConsoles(): Flux<String> {
        return logService.getConsoles()
    }
}