package com.jtmnetwork.monitor.entrypoint.socket

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.configuration.SpigotServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import org.bukkit.Server
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class MonitorConnectionTest {

    private val framework: Framework = mock()
    private val server: Server = mock()
    private val configuration: SpigotServerConfiguration = mock()
    private val dispatcher: EventDispatcher = mock()
    private val connection = SpigotMonitorConnection(framework, configuration, dispatcher)

    private val client: OkHttpClient = mock()
    private val socket: WebSocket = mock()

    @Before
    fun setup() {
        connection.setClient(client)

        `when`(framework.server).thenReturn(server)
    }

    @Test
    fun connect() {
        `when`(client.newWebSocket(anyOrNull(), anyOrNull())).thenReturn(socket)

        connection.connect()

        verify(client, times(1)).newWebSocket(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(client)
    }

    @Test
    fun disconnect() {
        connection.setSocket(socket)

        connection.disconnect()

        verify(socket, times(1)).close(anyInt(), anyOrNull())
        verifyNoMoreInteractions(socket)
    }

    @Test
    fun sendEvent() {
        connection.setSocket(socket)

        connection.sendEvent("connected_event", "test")

        verify(socket, times(1)).send(anyString())
        verifyNoMoreInteractions(socket)
    }
}