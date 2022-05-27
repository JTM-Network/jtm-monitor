package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(StoredPluginController::class)
@AutoConfigureWebTestClient
class StoredStoredPluginControllerTest {

    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    lateinit var pluginService: StoredPluginService

    private val plugin = StoredPlugin(name = "test", version = "1.0", path = "/")

    @Test
    fun getPlugin() {
        `when`(pluginService.getPlugin(anyOrNull())).thenReturn(Mono.just(plugin))

        client.get()
            .uri("/plugin/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test")
            .jsonPath("$.version").isEqualTo("1.0")

        verify(pluginService, times(1)).getPlugin(anyOrNull())
        verifyNoMoreInteractions(pluginService)
    }

    @Test
    fun getPluginByNameAndVersion() {
        `when`(pluginService.getPlugin(anyString(), anyString())).thenReturn(Mono.just(plugin))

        client.get()
            .uri("/plugin?name=test&version='1.0'")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test")
            .jsonPath("$.version").isEqualTo("1.0")

        verify(pluginService, times(1)).getPlugin(anyString(), anyString())
        verifyNoMoreInteractions(pluginService)
    }

    @Test
    fun getPlugins() {
        `when`(pluginService.getPlugins()).thenReturn(Flux.just(plugin, StoredPlugin(name = "test #2", version = "0.1", path = "/test.jar")))

        client.get()
            .uri("/plugin/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].name").isEqualTo("test")
            .jsonPath("$[0].version").isEqualTo("1.0")
            .jsonPath("$[0].path").isEqualTo("/")
            .jsonPath("$[1].name").isEqualTo("test #2")
            .jsonPath("$[1].version").isEqualTo("0.1")
            .jsonPath("$[1].path").isEqualTo("/test.jar")

        verify(pluginService, times(1)).getPlugins()
        verifyNoMoreInteractions(pluginService)
    }

    @Test
    fun deletePlugin() {
        `when`(pluginService.removePlugin(anyOrNull())).thenReturn(Mono.just(plugin))

        client.delete()
            .uri("/plugin/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test")
            .jsonPath("$.version").isEqualTo("1.0")

        verify(pluginService, times(1)).removePlugin(anyOrNull())
        verifyNoMoreInteractions(pluginService)
    }
}