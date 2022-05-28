package com.jtmnetwork.monitor.entrypoint.handler

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import okhttp3.WebSocket
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory

class CommandHandler @Inject constructor(private val framework: Framework): EventHandlerImpl() {

    private val logger = LoggerFactory.getLogger(CommandHandler::class.java)

    override fun onEvent(socket: WebSocket, event: Event) {
        framework.runTask { framework.server.dispatchCommand(framework.server.consoleSender, event.value) }
    }
}