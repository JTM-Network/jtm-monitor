package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import com.jtmnetwork.monitor.service.data.cache.ServerCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ServerService @Autowired constructor(private val repository: ServerRepository, private val cache: ServerCache) {

    /**
     * Store new server information indexed.
     *
     * @param server        the server to be inserted
     * @return              the server that was saved.
     */
    fun insert(server: Server): Mono<Server> {
        return repository.findAll()
            .collectList()
            .flatMap { repository.save(Server(id = server.id, index = (it.size + 1))) }
            .flatMap { cache.save(it).thenReturn(it) }
    }

    /**
     * Update the server information saved.
     *
     * @param server        the update server
     * @return              the updated server
     */
    fun update(server: Server): Mono<Server> {
        return repository.findById(server.id)
            .flatMap { repository.save(it.update(server)) }
            .flatMap { cache.save(it).thenReturn(it) }
    }

    /**
     * Find server by identifier
     *
     * @param id            the identifier.
     * @return              the server found.
     */
    fun findById(id: UUID): Mono<Server> {
        return repository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(ServerNotFound()) })
    }

    /**
     * Find all servers.
     *
     * @return              the servers that have been saved.
     */
    fun findAll(): Flux<Server> {
        return repository.findAll()
    }

    /**
     * Delete the server by identifier.
     *
     * @param id            the identifier
     * @return              the deleted server.
     */
    fun deleteById(id: UUID): Mono<Server> {
        return repository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(ServerNotFound()) })
            .flatMap { repository.delete(it).thenReturn(it) }
    }
}