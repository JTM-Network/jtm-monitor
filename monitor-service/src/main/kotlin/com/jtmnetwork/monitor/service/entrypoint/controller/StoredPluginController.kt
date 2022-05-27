package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/plugin")
class StoredPluginController @Autowired constructor(private val pluginService: StoredPluginService) {

    @GetMapping("/{id}")
    fun getPlugin(@PathVariable id: UUID): Mono<StoredPlugin> {
        return pluginService.getPlugin(id)
    }

    @GetMapping
    fun getPlugin(@RequestParam("name") name: String, @RequestParam("version") version: String): Mono<StoredPlugin> {
        return pluginService.getPlugin(name, version)
    }

    @GetMapping("/all")
    fun getPlugins(): Flux<StoredPlugin> {
        return pluginService.getPlugins()
    }

    @DeleteMapping("/{id}")
    fun deletePlugin(@PathVariable id: UUID): Mono<StoredPlugin> {
        return pluginService.removePlugin(id)
    }
}