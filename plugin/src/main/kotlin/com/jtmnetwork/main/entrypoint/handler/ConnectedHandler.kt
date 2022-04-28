package com.jtmnetwork.main.entrypoint.handler

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.jtmnetwork.main.core.domain.model.Event
import com.jtmnetwork.main.core.domain.model.ServerInfo
import com.jtmnetwork.main.core.usecase.handler.EventHandlerImpl
import com.jtmnetwork.main.entrypoint.configuration.ServerConfiguration
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

class ConnectedHandler @Inject constructor(private val configuration: ServerConfiguration): EventHandlerImpl() {

    private val gson = GsonBuilder().create()
    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)

    override fun onEvent(socket: WebSocket, event: Event) {
        val info = gson.fromJson(event.value, ServerInfo::class.java)
        configuration.setServerId(info.id)
        logger.info("Successfully connected to the server.")
    }
}