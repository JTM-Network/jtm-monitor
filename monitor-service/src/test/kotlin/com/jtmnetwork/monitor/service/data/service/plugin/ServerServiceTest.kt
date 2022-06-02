package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.exception.ServerNotFound
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import com.jtmnetwork.monitor.service.data.cache.ServerCache
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ServerServiceTest {

    private val repository: ServerRepository = mock()
    private val cache: ServerCache = mock()
    private val serverService = ServerService(repository, cache)

    private val server = Server(index = 1)

    @Test
    fun insert() {
        `when`(repository.findAll()).thenReturn(Flux.empty())
        `when`(repository.save(anyOrNull())).thenReturn(Mono.just(server))
        `when`(cache.save(anyOrNull())).thenReturn(Mono.just(1L))

        val inserted = serverService.insert(server)

        verify(repository, times(1)).findAll()
        verifyNoMoreInteractions(repository)

        StepVerifier.create(inserted)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun update() {
        `when`(repository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))
        `when`(repository.save(anyOrNull())).thenReturn(Mono.just(server))
        `when`(cache.save(anyOrNull())).thenReturn(Mono.just(1L))

        val updated = serverService.update(server)

        verify(repository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(repository)

        StepVerifier.create(updated)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun findById_thenNotFound() {
        `when`(repository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = serverService.findById(UUID.randomUUID())

        verify(repository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(repository)

        StepVerifier.create(returned)
            .expectError(ServerNotFound::class.java)
            .verify()
    }

    @Test
    fun findById() {
        `when`(repository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))

        val returned = serverService.findById(UUID.randomUUID())

        verify(repository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(repository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun findAll() {
        `when`(repository.findAll()).thenReturn(Flux.just(server))

        val returned = serverService.findAll()

        verify(repository, times(1)).findAll()
        verifyNoMoreInteractions(repository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun deleteById_thenNotFound() {
        `when`(repository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = serverService.deleteById(UUID.randomUUID())

        verify(repository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(repository)

        StepVerifier.create(returned)
            .expectError(ServerNotFound::class.java)
            .verify()
    }

    @Test
    fun deleteById() {
        `when`(repository.findById(any(UUID::class.java))).thenReturn(Mono.just(server))
        `when`(repository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = serverService.deleteById(UUID.randomUUID())

        verify(repository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(repository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }
}