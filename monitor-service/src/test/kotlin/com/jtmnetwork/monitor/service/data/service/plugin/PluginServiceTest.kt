package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PluginServiceTest {

    private val serverRepository: ServerRepository = mock()
    private val pluginService = PluginService(serverRepository)

    private val server = Server(index = 0)

    @Test
    fun updatePlugins_shouldSaveUpdatedPlugins() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))
        `when`(serverRepository.save(anyOrNull())).thenReturn(Mono.just(server))

        val returned = pluginService.updatePlugins(UUID.randomUUID(), mapOf())

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.id).isEqualTo(server.id)
                    assertThat(it.index).isEqualTo(0)
                }
                .verifyComplete()
    }

    @Test
    fun updatePlugins_shouldThrowNotFound() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = pluginService.updatePlugins(UUID.randomUUID(), mapOf())

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .expectError(ServerNotFound::class.java)
                .verify()
    }

    @Test
    fun enablePlugin_shouldSaveEnabledPlugin() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))
        `when`(serverRepository.save(anyOrNull())).thenReturn(Mono.just(server))

        val returned = pluginService.enablePlugin(UUID.randomUUID(), "test")

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.id).isEqualTo(server.id)
                    assertThat(it.index).isEqualTo(0)
                }
                .verifyComplete()
    }

    @Test
    fun enablePlugin_shouldThrowNotFound() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = pluginService.enablePlugin(UUID.randomUUID(), "test")

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .expectError(ServerNotFound::class.java)
                .verify()
    }

    @Test
    fun disablePlugin_shouldSaveDisabledPlugin() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))
        `when`(serverRepository.save(anyOrNull())).thenReturn(Mono.just(server))

        val returned = pluginService.disablePlugin(UUID.randomUUID(), "test")

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.id).isEqualTo(server.id)
                    assertThat(it.index).isEqualTo(0)
                }
                .verifyComplete()
    }

    @Test
    fun disablePlugin_shouldThrowNotFound() {
        `when`(serverRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = pluginService.disablePlugin(UUID.randomUUID(), "test")

        verify(serverRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(serverRepository)

        StepVerifier.create(returned)
                .expectError(ServerNotFound::class.java)
                .verify()
    }
}