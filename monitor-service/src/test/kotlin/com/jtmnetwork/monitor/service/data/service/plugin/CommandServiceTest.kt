package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.dto.PluginRequestDTO
import com.jtmnetwork.monitor.service.core.domain.exception.SessionNotFound
import com.jtmnetwork.monitor.service.core.domain.model.CommandDTO
import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CommandServiceTest {

    private val sessionRepository: SessionRepository = mock()
    private val commandService = CommandService(sessionRepository)

    private val session: Session = mock()
    private val dto = CommandDTO(UUID.randomUUID().toString(), "help")
    private val requestDto = PluginRequestDTO("id", "test")

    @Test
    fun sendCommand_shouldCompleteFunction() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(session)
        `when`(session.sendEvent(anyString(), anyOrNull())).thenReturn(Mono.empty())

        val returned = commandService.sendCommand(dto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        verify(session, times(1)).sendEvent(anyString(), anyOrNull())
        verifyNoMoreInteractions(session)

        StepVerifier.create(returned)
                .verifyComplete()
    }

    @Test
    fun sendCommand_shouldThrowNotFound() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(null)

        val returned = commandService.sendCommand(dto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
                .expectError(SessionNotFound::class.java)
                .verify()
    }

    @Test
    fun sendEnablePlugin_shouldSendRequest() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(session)
        `when`(session.sendEvent(anyString(), anyOrNull())).thenReturn(Mono.empty())

        val returned = commandService.sendEnablePlugin(requestDto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        verify(session, times(1)).sendEvent(anyString(), anyOrNull())
        verifyNoMoreInteractions(session)

        StepVerifier.create(returned)
                .verifyComplete()
    }

    @Test
    fun sendEnablePlugin_shouldThrowNotFound() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(null)

        val returned = commandService.sendEnablePlugin(requestDto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
                .expectError(SessionNotFound::class.java)
                .verify()
    }

    @Test
    fun sendDisablePlugin_shouldSendRequest() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(session)
        `when`(session.sendEvent(anyString(), anyOrNull())).thenReturn(Mono.empty())

        val returned = commandService.sendDisablePlugin(requestDto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        verify(session, times(1)).sendEvent(anyString(), anyOrNull())
        verifyNoMoreInteractions(session)

        StepVerifier.create(returned)
                .verifyComplete()
    }

    @Test
    fun sendDisablePlugin_shouldThrowNotFound() {
        `when`(sessionRepository.getSession(anyString())).thenReturn(null)

        val returned = commandService.sendDisablePlugin(requestDto)

        verify(sessionRepository, times(1)).getSession(anyString())
        verifyNoMoreInteractions(sessionRepository)

        StepVerifier.create(returned)
                .expectError(SessionNotFound::class.java)
                .verify()
    }
}