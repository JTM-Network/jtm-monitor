package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.PluginFound
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.PluginNotFound
import com.jtmnetwork.monitor.service.core.usecase.file.PluginFileHandler
import com.jtmnetwork.monitor.service.data.repository.StoredPluginRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class StoredPluginServiceTest {

    private val pluginRepository: StoredPluginRepository = mock()
    private val fileHandler: PluginFileHandler = mock()
    private val pluginService = StoredPluginService(pluginRepository, fileHandler)

    private val plugin = StoredPlugin(name = "test", version = "1.0", path = "/")

    @Test
    fun insertPlugin_thenFound() {
        `when`(pluginRepository.findByNameAndVersion(anyString(), anyString())).thenReturn(Mono.just(plugin))

        val returned = pluginService.insertPlugin("test", "1.0", "/")

        verify(pluginRepository, times(1)).findByNameAndVersion(anyString(), anyString())
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .expectError(PluginFound::class.java)
            .verify()
    }

    @Test
    fun insertPlugin() {
        `when`(pluginRepository.findByNameAndVersion(anyString(), anyString())).thenReturn(Mono.empty())
        `when`(pluginRepository.save(anyOrNull())).thenReturn(Mono.just(plugin))

        val returned = pluginService.insertPlugin("test", "1.0", "/")

        verify(pluginRepository, times(1)).findByNameAndVersion(anyString(), anyString())
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.version).isEqualTo("1.0")
                assertThat(it.path).isEqualTo("/")
            }
            .verifyComplete()
    }

    @Test
    fun getPlugin_thenNotFound() {
        `when`(pluginRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = pluginService.getPlugin(UUID.randomUUID())

        verify(pluginRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .expectError(PluginNotFound::class.java)
            .verify()
    }

    @Test
    fun getPlugin() {
        `when`(pluginRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(plugin))

        val returned = pluginService.getPlugin(UUID.randomUUID())

        verify(pluginRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.version).isEqualTo("1.0")
                assertThat(it.path).isEqualTo("/")
            }
            .verifyComplete()
    }

    @Test
    fun getPluginByNameAndVersion_thenNotFound() {
        `when`(pluginRepository.findByNameAndVersion(anyString(), anyString())).thenReturn(Mono.empty())

        val returned = pluginService.getPlugin("test", "1.0")

        verify(pluginRepository, times(1)).findByNameAndVersion(anyString(), anyString())
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .expectError(PluginNotFound::class.java)
            .verify()
    }

    @Test
    fun getPluginByNameAndVersion() {
        `when`(pluginRepository.findByNameAndVersion(anyString(), anyString())).thenReturn(Mono.just(plugin))

        val returned = pluginService.getPlugin("test", "1.0")

        verify(pluginRepository, times(1)).findByNameAndVersion(anyString(), anyString())
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.version).isEqualTo("1.0")
                assertThat(it.path).isEqualTo("/")
            }
            .verifyComplete()
    }

    @Test
    fun getPlugins() {
        `when`(pluginRepository.findAll()).thenReturn(Flux.just(plugin, StoredPlugin(name = "test #2", version = "0.1", path = "/test.jar")))

        val returned = pluginService.getPlugins()

        verify(pluginRepository, times(1)).findAll()
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.version).isEqualTo("1.0")
                assertThat(it.path).isEqualTo("/")
            }
            .assertNext {
                assertThat(it.name).isEqualTo("test #2")
                assertThat(it.version).isEqualTo("0.1")
                assertThat(it.path).isEqualTo("/test.jar")
            }
            .verifyComplete()
    }

    @Test
    fun removePlugin_thenNotFound() {
        `when`(pluginRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = pluginService.removePlugin(UUID.randomUUID())

        verify(pluginRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .expectError(PluginNotFound::class.java)
            .verify()
    }

    @Test
    fun removePlugin() {
        `when`(pluginRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(plugin))
        `when`(pluginRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = pluginService.removePlugin(UUID.randomUUID())

        verify(pluginRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(pluginRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.version).isEqualTo("1.0")
                assertThat(it.path).isEqualTo("/")
            }
            .verifyComplete()
    }
}