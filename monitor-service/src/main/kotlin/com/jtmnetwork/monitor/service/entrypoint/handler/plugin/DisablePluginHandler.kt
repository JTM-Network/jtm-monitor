package com.jtmnetwork.monitor.service.entrypoint.handler.plugin

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.dto.PluginStatusDTO
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.service.plugin.PluginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class DisablePluginHandler @Autowired constructor(private val pluginService: PluginService): EventHandlerImpl("disable_plugin_event") {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        val dto = gson.fromJson(event.value, PluginStatusDTO::class.java)
        return pluginService.disablePlugin(dto.id, dto.name)
                .flatMap { Mono.empty() }
    }
}