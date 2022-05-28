package com.jtmnetwork.monitor.entrypoint.handler

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.model.Event
import okhttp3.WebSocket
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.ConsoleCommandSender
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class CommandHandlerTest {

    private val framework: Framework = mock()
    private val commandHandler = CommandHandler(framework)

    private val socket: WebSocket = mock()
    private val event = Event("command_event", "help")

    @Test
    fun onEvent_shouldSucceed() {
        commandHandler.onEvent(socket, event)

        verify(framework, times(1)).runTask(anyOrNull())
        verifyNoMoreInteractions(framework)
    }
}