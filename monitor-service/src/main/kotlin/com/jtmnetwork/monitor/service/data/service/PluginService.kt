package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.entity.Plugin
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.PluginFound
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.PluginNotFound
import com.jtmnetwork.monitor.service.data.repository.PluginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class PluginService @Autowired constructor(private val pluginRepository: PluginRepository) {

    /**
     * Inserts the plugin that has been downloaded.
     *
     * @param name              the name of the plugin.
     * @param version           the version of the plugin.
     * @param path              the path to the plugin file.
     * @return plugin           the plugin inserted
     * @see                     Plugin
     *
     * @throws PluginNotFound   if the plugin is not found.
     */
    fun insertPlugin(name: String, version: String, path: String): Mono<Plugin> {
        return pluginRepository.findByNameAndVersion(name, version)
            .flatMap<Plugin> { Mono.error(PluginFound()) }
            .switchIfEmpty(Mono.defer { pluginRepository.save(Plugin(name = name, version = version, path = path)) })
    }

    /**
     * Fetch the plugin using the identifier
     *
     * @param id                the identifier
     * @return plugin           the plugin found.
     * @see                     Plugin
     *
     * @throws PluginNotFound   if the plugin is not found.
     */
    fun getPlugin(id: UUID): Mono<Plugin> {
        return pluginRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PluginNotFound()) })
    }

    /**
     * Fetch the plugin using the name and the version.
     *
     * @param name              the name of the plugin
     * @param version           the version of the plugin
     * @return plugin           the plugin found.
     * @see                     Plugin
     *
     * @throws PluginNotFound   if the plugin is not found.
     */
    fun getPlugin(name: String, version: String): Mono<Plugin> {
        return pluginRepository.findByNameAndVersion(name, version)
            .switchIfEmpty(Mono.defer { Mono.error(PluginNotFound()) })
    }

    /**
     * Fetches all the plugins.
     *
     * @return plugin           the list of plugins
     * @see                     Plugin
     */
    fun getPlugins(): Flux<Plugin> = pluginRepository.findAll()

    /**
     * Removes the plugin using the identifier.
     *
     * @param id                the identifier
     * @return plugin           the deleted plugin.
     * @see                     Plugin
     *
     * @throws PluginNotFound   if the plugin is not found.
     */
    fun removePlugin(id: UUID): Mono<Plugin> {
        return pluginRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PluginNotFound()) })
            .flatMap { pluginRepository.delete(it).thenReturn(it) }
    }
}