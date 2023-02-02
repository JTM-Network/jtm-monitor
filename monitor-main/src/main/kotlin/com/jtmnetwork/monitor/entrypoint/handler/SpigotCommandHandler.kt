package com.jtmnetwork.monitor.entrypoint.handler

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

class SpigotCommandHandler @Inject constructor(private val framework: Framework): EventHandlerImpl("spigot_command_event") {

    private val logger = LoggerFactory.getLogger(SpigotCommandHandler::class.java)

    override fun onEvent(socket: WebSocket, event: Event) {
        logger.info("Processing command: ${event.value}")
        framework.runTask { framework.server.dispatchCommand(framework.server.consoleSender, event.value) }
    }
}