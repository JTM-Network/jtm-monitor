package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.data.service.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/server")
class ServerController @Autowired constructor(private val serverService: ServerService) {

    @GetMapping("/{id}")
    fun getServer(@PathVariable id: UUID): Mono<Server> {
        return serverService.findById(id)
    }

    @GetMapping("/all")
    fun getServers(): Flux<Server> {
        return serverService.findAll()
    }
}