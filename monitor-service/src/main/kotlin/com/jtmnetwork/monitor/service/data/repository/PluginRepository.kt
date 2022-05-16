package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.domain.entity.Plugin
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface PluginRepository: ReactiveMongoRepository<Plugin, UUID> {

    fun findByName(name: String): Mono<Plugin>

    fun findByNameAndVersion(name: String): Mono<Plugin>
}