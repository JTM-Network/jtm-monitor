package com.jtmnetwork.monitor.entrypoint.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.core.domain.model.serverinfo.SpigotServerInfo
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.usecase.log.SpigotLogReporter
import com.jtmnetwork.monitor.core.util.TestUtil
import com.jtmnetwork.monitor.entrypoint.configuration.SpigotServerConfiguration
import com.jtmnetwork.monitor.entrypoint.socket.SpigotMonitorConnection
import okhttp3.WebSocket
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import java.util.*

@RunWith(MockitoJUnitRunner.Silent::class)
class SpigotConnectedHandlerTest {

    private val pluginManager = TestUtil.createPluginManager()
    private val framework = TestUtil.createFramework(pluginManager)
    private val connection: SpigotMonitorConnection = TestUtil.createConnection(framework)
    private val reporter: SpigotLogReporter = mock()
    private val configuration: SpigotServerConfiguration = mock()
    private val spigotConnectedHandler = spy(SpigotConnectedHandler(connection, reporter, configuration))
    private val socket: WebSocket = mock()
    private val event = Event("connected_event", GsonBuilder().create().toJson(SpigotServerInfo("id", "Test", "", "1.16.R01-SNAPSHOT", "1.16.R01-SNAPSHOT", 15, 25565, 14)))

    @Test
    fun onEvent_shouldSucceed() {
        `when`(configuration.getServerId()).thenReturn(UUID.randomUUID().toString())

        spigotConnectedHandler.onEvent(socket, event)

        verify(configuration, times(1)).setServerId(anyString())
        verify(configuration, times(1)).getServerId()
        verifyNoMoreInteractions(configuration)

        verify(reporter, times(1)).getLogs()
        verify(reporter, times(1)).init()
        verifyNoMoreInteractions(reporter)

        verify(connection, times(1)).framework
        verifyNoMoreInteractions(connection)

        verify(spigotConnectedHandler, times(1)).getGson()
        verify(spigotConnectedHandler, times(1)).onEvent(anyOrNull(), anyOrNull())
        verify(spigotConnectedHandler, times(2)).sendEvent(anyOrNull(),  anyString(), anyOrNull())
        verify(spigotConnectedHandler, times(1)).fetchPlugins()
        verifyNoMoreInteractions(spigotConnectedHandler)
    }
}