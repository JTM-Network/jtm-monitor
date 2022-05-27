package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
class SessionServiceTest {

    private val sessionRepository: SessionRepository = mock()
    private val logRepository: LogRepository = mock()
    private val sessionService = SessionService(sessionRepository, logRepository)
    private val session: Session = mock()
    private val socketSession: WebSocketSession = mock()

    @Before
    fun setup() {
        `when`(session.id).thenReturn("test")
        `when`(socketSession.id).thenReturn("test")
    }

    @Test
    fun insert() {
        `when`(sessionRepository.addSession(anyOrNull())).thenReturn(session)

        val returned = sessionService.insert(socketSession)

        verify(sessionRepository, times(1)).addSession(anyOrNull())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.id).isEqualTo("test")
            }
            .verifyComplete()
    }

    @Test
    fun findById() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(session)

        val returned = sessionService.findById("test")

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.id).isEqualTo("test")
            }
            .verifyComplete()
    }

    @Test
    fun findAll() {
        `when`(sessionRepository.getSessions()).thenReturn(listOf(session))

        val returned = sessionService.findAll()

        verify(sessionRepository, times(1)).getSessions()
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.id).isEqualTo("test")
            }
            .verifyComplete()
    }

    @Test
    fun deleteById() {
        `when`(sessionRepository.removeSession(anyString())).thenReturn(session)

        val returned = sessionService.deleteById("test")

        verify(sessionRepository, times(1)).removeSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.id).isEqualTo("test")
            }
            .verifyComplete()
    }
}