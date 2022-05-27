package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.model.Plugin
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PluginService @Autowired constructor(private val serverRepository: ServerRepository) {

    fun enablePlugin(name: String): Mono<Plugin> {
        return Mono.empty()
    }

    fun disablePlugin(name: String): Mono<Plugin> {
        return Mono.empty()
    }

    fun updatePlugins(plugins: List<Plugin>): Mono<List<Plugin>> {
        return Mono.empty()
    }
}