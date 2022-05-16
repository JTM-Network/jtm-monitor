package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.entity.Plugin
import com.jtmnetwork.monitor.service.data.repository.PluginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class PluginService @Autowired constructor(private val pluginRepository: PluginRepository) {

    fun insertPlugin(name: String, version: String): Mono<Plugin> {
        return Mono.empty()
    }

    fun getPlugin(id: UUID): Mono<Plugin> {
        return Mono.empty()
    }

    fun getPlugin(name: String, version: String): Mono<Plugin> {
        return Mono.empty()
    }

    fun getPlugins(): Flux<Plugin> {
        return Flux.empty()
    }

    fun removePlugin(id: UUID): Mono<Plugin> {
        return Mono.empty()
    }

}