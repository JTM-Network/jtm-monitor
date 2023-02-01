package com.jtmnetwork.monitor.entrypoint.handler

import com.jtmnetwork.monitor.core.domain.model.Event
import com.jtmnetwork.monitor.core.domain.model.UpdateResponse
import com.jtmnetwork.monitor.core.usecase.handler.EventHandlerImpl
import okhttp3.WebSocket
import org.slf4j.LoggerFactory

class UpdateResponseHandler: EventHandlerImpl("update_response_event") {

    private val logger = LoggerFactory.getLogger(UpdateResponseHandler::class.java)

    override fun onEvent(socket: WebSocket, event: Event) {
        val response = getGson().fromJson(event.value, UpdateResponse::class.java)
        logger.info("Monitor: ${response.reason}")
    }
}