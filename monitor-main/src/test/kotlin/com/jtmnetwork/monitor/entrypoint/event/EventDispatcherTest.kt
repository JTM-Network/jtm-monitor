package com.jtmnetwork.monitor.entrypoint.event

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.usecase.handler.EventHandler
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import com.jtmnetwork.monitor.data.repository.EventRepository
import okhttp3.WebSocket
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class EventDispatcherTest {

    private val repository: EventRepository = mock()
    private val dispatcher = EventDispatcher(repository)

    private val handler: EventHandler = mock()
    private val socket: WebSocket = mock()
    private val event = Event("connected_event", "test")

    @Test
    fun dispatch_shouldSucceed() {
        `when`(repository.getHandler(anyString())).thenReturn(handler)

        dispatcher.dispatch(socket, GsonBuilder().create().toJson(event))

        verify(repository, times(1)).getHandler(anyString())
        verifyNoMoreInteractions(repository)

        verify(handler, times(1)).onEvent(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(handler)
    }

    @Test
    fun sendEvent_shouldSucceed() {
        dispatcher.sendEvent(socket, "connected_event", "test")

        verify(socket, times(1)).send(anyString())
        verifyNoMoreInteractions(socket)
    }
}