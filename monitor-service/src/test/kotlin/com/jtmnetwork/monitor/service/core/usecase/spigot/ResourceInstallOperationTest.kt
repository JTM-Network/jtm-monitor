package com.jtmnetwork.monitor.service.core.usecase.spigot

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.core.domain.model.spigot.SpigotInstallRequest
import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import com.jtmnetwork.monitor.service.data.service.plugin.PluginService
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
class ResourceInstallOperationTest {

    private val sessionService: SessionService = mock()
    private val pluginService: StoredPluginService = mock()
    private val client: OkHttpClient = mock()
    private val installRequest = SpigotInstallRequest("server", "test", "1.0", UUID.randomUUID().toString())

    private val installOperation = ResourceInstallOperation(sessionService, pluginService, installRequest)
    private val observer: OperationObserver = mock()
    private val executor = OperationExecutor(observer = observer, operation = installOperation)

    private val session: Session = mock()
    private val call: Call = mock()
    private val res: Response = mock()

    @Before
    fun setup() {
        `when`(client.newCall(anyOrNull())).thenReturn(call)
        `when`(call.execute()).thenReturn(res)
    }

    @Test
    fun execute_shouldInstall_whenFindPluginLocally() {
        `when`(sessionService.findById(anyString())).thenReturn(Mono.just(session))
        `when`(pluginService.getPlugin(anyString(), anyString())).thenReturn(Mono.just(StoredPlugin(name = "test", version = "1.0", path = "")))

        installOperation.execute(executor)

        verify(observer, times(1)).sendMessage(anyOrNull(), anyString())
        verifyNoMoreInteractions(observer)

        verify(client, times(1)).newCall(anyOrNull())
        verifyNoMoreInteractions(client)

        verify(call, times(1)).execute()
        verifyNoMoreInteractions(call)
    }
}