package com.jtmnetwork.monitor.entrypoint.log

import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.message.Message
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class LogAppenderTest {

    private val connection: MonitorConnection = mock()
    private val appender = LogAppender(connection)

    @Test
    fun append_shouldSendEvent() {
        val logEvent: LogEvent = mock()
        val message: Message = mock()

        `when`(logEvent.message).thenReturn(message)
        `when`(message.format).thenReturn("test format")

        appender.append(logEvent)

        verify(logEvent, times(1)).message
        verifyNoMoreInteractions(logEvent)

        verify(message, times(1)).format
        verifyNoMoreInteractions(message)

        verify(connection, times(1)).sendEvent(anyString(), anyOrNull())
        verifyNoMoreInteractions(connection)
    }
}