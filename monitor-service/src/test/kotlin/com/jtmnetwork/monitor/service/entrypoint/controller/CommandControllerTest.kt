package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.model.CommandDTO
import com.jtmnetwork.monitor.service.data.service.plugin.CommandService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@WebFluxTest(CommandController::class)
@AutoConfigureWebTestClient
class CommandControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var commandService: CommandService

    @Test
    fun sendCommand() {
        `when`(commandService.sendSpigotCommand(anyOrNull())).thenReturn(Mono.empty())

        testClient.post()
            .uri("/command")
            .bodyValue(CommandDTO("id", "test"))
            .exchange()
            .expectStatus().isOk

        verify(commandService, times(1)).sendSpigotCommand(anyOrNull())
        verifyNoMoreInteractions(commandService)
    }
}