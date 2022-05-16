package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.data.service.ServerService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(ServerController::class)
@AutoConfigureWebTestClient
class ServerControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var serverService: ServerService

    private val created = Server(index = 1)

    @Test
    fun getServer() {
        `when`(serverService.findById(anyOrNull())).thenReturn(Mono.just(created))

        testClient.get()
            .uri("/server/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(created.id.toString())
            .jsonPath("$.index").isEqualTo(1)

        verify(serverService, times(1)).findById(anyOrNull())
        verifyNoMoreInteractions(serverService)
    }

    @Test
    fun getServers() {
        `when`(serverService.findAll()).thenReturn(Flux.just(created))

        testClient.get()
            .uri("/server/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(created.id.toString())
            .jsonPath("$[0].index").isEqualTo(1)

        verify(serverService, times(1)).findAll()
        verifyNoMoreInteractions(serverService)
    }
}