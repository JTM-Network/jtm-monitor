package com.jtmnetwork.monitor.entrypoint.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.usecase.log.LogReporter
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import okhttp3.WebSocket
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class ConnectedHandlerTest {

    private val connection: MonitorConnection = mock()
    private val reporter: LogReporter = mock()
    private val configuration: ServerConfiguration = mock()

    private val connectedHandler = ConnectedHandler(connection, reporter, configuration)

    private val socket: WebSocket = mock()
    private val event = Event("connected_event", GsonBuilder().create().toJson(ServerInfo("id", "Test", "", "1.16.R01-SNAPSHOT", "1.16.R01-SNAPSHOT", 15, 25565, 14)))

    @Test
    fun onEvent_shouldSucceed() {
        connectedHandler.onEvent(socket, event)

        verify(configuration, times(1)).setServerId(anyString())
        verifyNoMoreInteractions(configuration)

        verify(reporter, times(1)).getLogs()
        verify(reporter, times(1)).init()
        verifyNoMoreInteractions(reporter)

        verify(connection, times(1)).sendEvent(anyString(), anyOrNull())
        verifyNoMoreInteractions(connection)
    }
}