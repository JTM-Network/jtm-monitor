package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.dto.PluginRequestDTO
import com.jtmnetwork.monitor.service.data.service.plugin.CommandService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@WebFluxTest(PluginController::class)
@AutoConfigureWebTestClient
class PluginControllerTest {

    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    lateinit var commandService: CommandService

    private val dto = PluginRequestDTO("id", "test")

    @Test
    fun sendEnablePlugin() {
        `when`(commandService.sendEnablePlugin(anyOrNull())).thenReturn(Mono.empty())

        client.post()
                .uri("/plugin/enable")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk

        verify(commandService, times(1)).sendEnablePlugin(anyOrNull())
        verifyNoMoreInteractions(commandService)
    }

    @Test
    fun sendDisablePlugin() {
        `when`(commandService.sendDisablePlugin(anyOrNull())).thenReturn(Mono.empty())

        client.post()
                .uri("/plugin/disable")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk

        verify(commandService, times(1)).sendDisablePlugin(anyOrNull())
        verifyNoMoreInteractions(commandService)
    }
}