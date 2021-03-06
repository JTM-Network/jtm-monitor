package com.jtmnetwork.monitor.service.entrypoint.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.model.ServerInfo
import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.service.DiscordService
import com.jtmnetwork.monitor.service.data.service.ServerService
import com.jtmnetwork.monitor.service.data.service.SessionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class ConnectedHandlerTest {

    private val serverService: ServerService = mock()
    private val sessionService: SessionService = mock()
    private val discordService: DiscordService = mock()
    private val connectedHandler = ConnectedHandler(serverService, sessionService, discordService)

    private val socket: WebSocketSession = mock()
    private val event = Event("connected_event", GsonBuilder().create().toJson(ServerInfo(UUID.randomUUID().toString(), "test", "0.1", "1.18", "1.18", 20, 25565, 14)))
    private val session = Session("id", socket)
    private val server = Server(index = 1)
    private val message: WebSocketMessage = mock()

    @Before
    fun setup() {
        `when`(socket.id).thenReturn("id")
        `when`(message.payloadAsText).thenReturn("test")
    }

    @Test
    fun onEvent() {
        `when`(sessionService.insert(anyOrNull())).thenReturn(Mono.just(session))
        `when`(serverService.findById(anyOrNull())).thenReturn(Mono.just(server))
        `when`(socket.textMessage(anyString())).thenReturn(message)

        val message = connectedHandler.onEvent(socket, event)

        verify(sessionService, times(1)).insert(anyOrNull())
        verifyNoMoreInteractions(sessionService)

        StepVerifier.create(message)
            .assertNext {
                assertThat(it.payloadAsText).isEqualTo("test")
            }
            .verifyComplete()
    }
}