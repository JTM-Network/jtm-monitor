package com.jtmnetwork.monitor.service.data.cache

import com.jtmnetwork.monitor.service.core.domain.entity.CachedServer
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ServerCache @Autowired constructor(private val redisOperations: ReactiveRedisOperations<String, CachedServer>) {

    fun save(server: Server): Mono<Long> {
        return redisOperations.opsForList().rightPush("servers", CachedServer(server))
    }

    fun findAll(): Flux<CachedServer> {
        return redisOperations.opsForList().range("servers", 0, -1)
    }

    fun findById(id: String): Mono<CachedServer> {
        return findAll().filter { s -> s.server.id.toString() == id }.last()
    }

    fun deleteAll(): Mono<Boolean> {
        return redisOperations.opsForList().delete("servers")
    }
}