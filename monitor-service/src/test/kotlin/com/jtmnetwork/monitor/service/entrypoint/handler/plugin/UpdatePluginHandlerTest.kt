package com.jtmnetwork.monitor.service.entrypoint.handler.plugin

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.dto.PluginDTO
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.service.plugin.PluginService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
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
class UpdatePluginHandlerTest {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val pluginService: PluginService = mock()
    private val handler = UpdatePluginHandler(pluginService)

    private val session: WebSocketSession = mock()
    private val event = Event("update_plugins_event", gson.toJson(PluginDTO(UUID.randomUUID(), mutableMapOf())))

    @Test
    fun onEvent_shouldUpdatePlugins() {
        Mockito.`when`(pluginService.updatePlugins(anyOrNull(), anyOrNull())).thenReturn(Mono.empty())

        val returned = handler.onEvent(session, event)

        verify(pluginService, Mockito.times(1)).updatePlugins(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(pluginService)

        StepVerifier.create(returned)
                .verifyComplete()
    }

    @Test
    fun onEvent_shouldThrowServerNotFound() {
        `when`(pluginService.updatePlugins(anyOrNull(), anyOrNull())).thenReturn(Mono.error(ServerNotFound()))

        val returned = handler.onEvent(session, event)

        verify(pluginService, times(1)).updatePlugins(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(pluginService)

        StepVerifier.create(returned)
                .expectError(ServerNotFound::class.java)
                .verify()
    }
}