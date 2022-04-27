package com.jtmnetwork.main.entrypoint.handler

import com.google.gson.GsonBuilder
import com.google.inject.Inject
import com.jtmnetwork.main.core.domain.model.Event
import com.jtmnetwork.main.core.domain.model.ServerInfo
import com.jtmnetwork.main.core.usecase.handler.EventHandlerImpl
import com.jtmnetwork.main.data.repository.EventRepository
import com.jtmnetwork.main.entrypoint.configuration.ServerConfiguration
import okhttp3.WebSocket
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct

class ConnectedHandler @Inject constructor(private val configuration: ServerConfiguration, private val eventRepository: EventRepository): EventHandlerImpl("connected_event", eventRepository) {

    private val gson = GsonBuilder().create()
    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)

    @PostConstruct
    fun init() {
        eventRepository.registerHandler("connected_event", this)
    }

    override fun onEvent(socket: WebSocket, event: Event) {
        val info = gson.fromJson(event.value, ServerInfo::class.java)
        configuration.setServerId(info.id)
        logger.info("Successfully connected to the server.")
    }
}