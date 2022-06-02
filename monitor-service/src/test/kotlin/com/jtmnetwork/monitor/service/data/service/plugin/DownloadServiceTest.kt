package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.DownloadLog
import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.core.usecase.file.PluginFileHandler
import com.jtmnetwork.monitor.service.data.repository.DownloadLogRepository
import com.jtmnetwork.monitor.service.data.repository.StoredPluginRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.core.io.Resource
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class DownloadServiceTest {

    private val logRepository: DownloadLogRepository = mock()
    private val pluginRepository: StoredPluginRepository = mock()
    private val fileHandler: PluginFileHandler = mock()

    private val downloadService = DownloadService(logRepository, pluginRepository, fileHandler)

    private val plugin = StoredPlugin(name = "test", version = "1.0", path = "/test.jar")
    private val resource: Resource = mock()

    @Before
    fun setup() {
        `when`(resource.filename).thenReturn("test.jar")
    }

    @Test
    fun downloadPlugin_shouldReturnResource() {
        `when`(pluginRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(plugin))
        `when`(fileHandler.fetch(anyString())).thenReturn(Mono.just(resource))

        val returned = downloadService.downloadPlugin("server", UUID.randomUUID().toString())

        verify(pluginRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.filename).isEqualTo("test.jar")
                }
                .verifyComplete()
    }
}