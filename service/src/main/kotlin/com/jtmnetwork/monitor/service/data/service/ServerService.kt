package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import com.jtmnetwork.monitor.service.data.cache.ServerCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ServerService @Autowired constructor(private val repository: ServerRepository, private val cache: ServerCache) {

    fun insert(server: Server): Mono<Server> {
        return repository.findAll()
            .collectList()
            .flatMap { repository.save(Server(id = server.id, index = (it.size + 1))) }
            .flatMap { cache.save(it).thenReturn(it) }
    }

    fun update(server: Server): Mono<Server> {
        return repository.findById(server.id)
            .flatMap { repository.save(it.update(server)) }
            .flatMap { cache.save(it).thenReturn(it) }
    }

    fun findById(id: UUID): Mono<Server> {
        return repository.findById(id)
    }

    fun findAll(): Flux<Server> {
        return repository.findAll()
    }

    fun deleteById(id: UUID): Mono<Server> {
        return repository.findById(id)
            .flatMap { repository.delete(it).thenReturn(it) }
    }
}