package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface StoredPluginRepository: ReactiveMongoRepository<StoredPlugin, UUID> {

    fun findByName(name: String): Mono<StoredPlugin>

    fun findByNameAndVersion(name: String, version: String): Mono<StoredPlugin>
}