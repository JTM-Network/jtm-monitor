package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.exception.ConsoleNotFound
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import reactor.core.publisher.Sinks
import reactor.test.StepVerifier

@RunWith(MockitoJUnitRunner::class)
class LogServiceTest {

    private val logRepository: LogRepository = mock()
    private val logService = LogService(logRepository)

    @Test
    fun getConsoleLogs_thenNotFound() {
        `when`(logRepository.getSink(anyString())).thenReturn(null)

        val returned = logService.getConsoleLogs("id")

        verify(logRepository, times(1)).getSink(anyString())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .expectError(ConsoleNotFound::class.java)
            .verify()
    }

    @Test
    fun getConsoleLogs() {
        `when`(logRepository.getSink(anyString())).thenReturn(Sinks.many().replay().all())

        val returned = logService.getConsoleLogs("id")

        verify(logRepository, times(1)).getSink(anyString())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it).isInstanceOf(String::class.java)
            }
            .expectComplete()
    }
}