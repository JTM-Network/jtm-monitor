package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.domain.model.Plugin
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PluginService @Autowired constructor(private val serverRepository: ServerRepository) {

    /**
     * Update the servers plugins.
     *
     * @param id                    the server identifier.
     * @param plugins               the plugins to update with.
     * @return                      the server they updated.
     * @see                         Server
     *
     * @throws ServerNotFound       if the server id can't be found.
     */
    fun updatePlugins(id: UUID, plugins: Map<String, Plugin>): Mono<Server> {
        return serverRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(ServerNotFound()) })
                .flatMap { serverRepository.save(it.updatePlugins(plugins)) }
    }

    /**
     * Enable the plugin using the server identifier & plugin name.
     *
     * @param id                    the server identifier.
     * @param name                  the plugin name.
     * @return                      the server they enabled the plugin on.
     * @see                         Server
     */
    fun enablePlugin(id: UUID, name: String): Mono<Server> {
        return serverRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(ServerNotFound()) })
                .flatMap { serverRepository.save(it.enable(name)) }
    }

    /**
     * Disable the plugin using the server identifier & plugin name.
     *
     * @param id                    the server identifier.
     * @param name                  the plugin name.
     * @return                      the server they disabled the plugin on.
     * @see                         Server
     */
    fun disablePlugin(id: UUID, name: String): Mono<Server> {
        return serverRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(ServerNotFound()) })
                .flatMap { serverRepository.save(it.disable(name)) }
    }
}