package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/session")
class SessionController @Autowired constructor(private val sessionService: SessionService) {

    @GetMapping("/{id}")
    fun getSession(@PathVariable id: String): Mono<Session> {
        return sessionService.findById(id)
    }

    @GetMapping("/all")
    fun getSessions(): Flux<Session> {
        return sessionService.findAll()
    }

    @DeleteMapping("/{id}")
    fun deleteSession(@PathVariable id: String): Mono<Session> {
        return sessionService.deleteById(id)
    }
}