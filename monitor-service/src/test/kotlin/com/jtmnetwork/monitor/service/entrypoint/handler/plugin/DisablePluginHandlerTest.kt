package com.jtmnetwork.monitor.service.entrypoint.handler.plugin

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.dto.PluginDTO
import com.jtmnetwork.monitor.service.core.domain.dto.PluginStatusDTO
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.service.plugin.PluginService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DisablePluginHandlerTest {

    private val pluginService: PluginService = mock()
    private val handler = DisablePluginHandler(pluginService)

    private val session: WebSocketSession = mock()
    private val event = Event("disable_plugin_event", GsonBuilder().create().toJson(PluginStatusDTO(UUID.randomUUID(), "test")))
    private val server = Server(index = 0)

    @Test
    fun onEvent_shouldComplete() {
        `when`(pluginService.disablePlugin(anyOrNull(), anyString())).thenReturn(Mono.just(server))

        val returned = handler.onEvent(session, event)

        verify(pluginService, times(1)).disablePlugin(anyOrNull(), anyString())
        verifyNoMoreInteractions(pluginService)

        StepVerifier.create(returned)
                .verifyComplete()
    }

    @Test
    fun onEvent_shouldThrowNotFound() {
        `when`(pluginService.disablePlugin(anyOrNull(), anyString())).thenReturn(Mono.error(ServerNotFound()))

        val returned = handler.onEvent(session, event)

        verify(pluginService, times(1)).disablePlugin(anyOrNull(), anyString())
        verifyNoMoreInteractions(pluginService)

        StepVerifier.create(returned)
                .expectError(ServerNotFound::class.java)
                .verify()
    }
}