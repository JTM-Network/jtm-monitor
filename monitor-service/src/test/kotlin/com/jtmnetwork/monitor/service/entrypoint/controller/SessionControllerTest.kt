package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@WebFluxTest(SessionController::class)
class SessionControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var sessionService: SessionService

    private val session: Session = mock()

    @Before
    fun setup() {
        `when`(session.id).thenReturn("test")
    }

    @Test
    fun getSession() {
        `when`(sessionService.findById(anyString())).thenReturn(Mono.just(session))

        testClient.get()
            .uri("/session/test")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("test")

        verify(sessionService, times(1)).findById(anyString())
        verifyNoMoreInteractions(sessionService)
    }

    @Test
    fun getSessions() {
        `when`(sessionService.findAll()).thenReturn(Flux.just(session))

        testClient.get()
            .uri("/session/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("test")

        verify(sessionService, times(1)).findAll()
        verifyNoMoreInteractions(sessionService)
    }

    @Test
    fun deleteSession() {
        `when`(sessionService.deleteById(anyString())).thenReturn(Mono.just(session))

        testClient.delete()
            .uri("/session/test")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("test")

        verify(sessionService, times(1)).deleteById(anyString())
        verifyNoMoreInteractions(sessionService)
    }
}