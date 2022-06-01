package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.model.Plugin
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PluginService @Autowired constructor(private val serverRepository: ServerRepository) {

    fun updatePlugins(id: UUID, plugins: Map<String, Plugin>): Mono<Server> {
        return serverRepository.findById(id)
                .flatMap { serverRepository.save(it.updatePlugins(plugins)) }
    }

    fun enablePlugin(id: UUID, name: String): Mono<Server> {
        return serverRepository.findById(id)
                .flatMap { serverRepository.save(it.enable(name)) }
    }

    fun disablePlugin(id: UUID, name: String): Mono<Server> {
        return serverRepository.findById(id)
                .flatMap { serverRepository.save(it.disable(name)) }
    }
}