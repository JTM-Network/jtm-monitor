package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.entity.Plugin
import com.jtmnetwork.monitor.service.data.service.PluginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/plugin")
class PluginController @Autowired constructor(private val pluginService: PluginService) {

    @GetMapping("/{id}")
    fun getPlugin(@PathVariable id: UUID): Mono<Plugin> {
        return pluginService.getPlugin(id)
    }

    @GetMapping
    fun getPlugin(@RequestParam("name") name: String, @RequestParam("version") version: String): Mono<Plugin> {
        return pluginService.getPlugin(name, version)
    }

    @GetMapping("/all")
    fun getPlugins(): Flux<Plugin> {
        return pluginService.getPlugins()
    }

    @DeleteMapping("/{id}")
    fun deletePlugin(@PathVariable id: UUID): Mono<Plugin> {
        return pluginService.removePlugin(id)
    }
}