package com.jtmnetwork.monitor.entrypoint.socket

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.configuration.SpigotServerConfiguration
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import okhttp3.Response
import okhttp3.WebSocket
import org.bukkit.Server
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import java.util.*

class MonitorListenerTest {

    private val framework: Framework = mock()
    private val server: Server = mock()
    private val configuration: SpigotServerConfiguration = mock()
    private val dispatcher: EventDispatcher = mock()
    private lateinit var monitorListener: SpigotMonitorListener

    private val socket: WebSocket = mock()
    private val res: Response = mock()

    @Before
    fun setup() {
        `when`(framework.server).thenReturn(server)
        `when`(configuration.getServerId()).thenReturn(UUID.randomUUID().toString())

        monitorListener = SpigotMonitorListener(framework, configuration, dispatcher)
    }

    @Test
    fun onMessage() {
        monitorListener.onMessage(socket, "test")

        verify(dispatcher, times(1)).dispatch(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(dispatcher)
    }

    @Test
    fun onOpen() {
        `when`(server.name).thenReturn("hi")
        `when`(server.version).thenReturn("1.16")
        `when`(server.bukkitVersion).thenReturn("1.16.R01-SNAPSHOT")
        `when`(server.minecraftVersion).thenReturn("1.16.R01-SNAPSHOT")
        `when`(server.maxPlayers).thenReturn(20)
        `when`(server.port).thenReturn(25565)
        `when`(server.viewDistance).thenReturn(10)

        monitorListener.onOpen(socket, res)

        verify(dispatcher, times(1)).sendEvent(anyOrNull(), anyString(), anyOrNull())
        verifyNoMoreInteractions(dispatcher)

        verify(server, times(1)).name
        verify(server, times(1)).version
        verify(server, times(1)).bukkitVersion
        verify(server, times(1)).minecraftVersion
        verify(server, times(1)).maxPlayers
        verify(server, times(1)).port
        verify(server, times(1)).viewDistance
        verifyNoMoreInteractions(server)

        verify(configuration, times(1)).getServerId()
        verifyNoMoreInteractions(configuration)
    }
}