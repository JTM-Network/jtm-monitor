package com.jtmnetwork.monitor.service.entrypoint.handler.log

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.model.ServerLog
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import com.jtmnetwork.monitor.service.entrypoint.handler.log.IncomingLogHandler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.socket.WebSocketSession
import java.util.*

@RunWith(SpringRunner::class)
class IncomingLogHandlerTest {

    private val logRepository: LogRepository = mock()
    private val logHandler = IncomingLogHandler(logRepository)

    private val session: WebSocketSession = mock()
    private val event = Event("incoming_log_event", GsonBuilder().create().toJson(ServerLog(LinkedList(listOf("test..")))))

    @Before
    fun setup() {
        `when`(session.id).thenReturn("id")
    }

    @Test
    fun onEvent() {
        logHandler.onEvent(session, event)

        verify(logRepository, times(1)).sendMessage(anyString(), anyString())
        verifyNoMoreInteractions(logRepository)

        verify(session, times(1)).id
        verifyNoMoreInteractions(session)
    }
}