package com.jtmnetwork.monitor.service.entrypoint.handler

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.domain.model.ServerInfo
import com.jtmnetwork.monitor.service.core.domain.model.event.Event
import com.jtmnetwork.monitor.service.core.usecase.event.EventHandlerImpl
import com.jtmnetwork.monitor.service.data.service.DiscordService
import com.jtmnetwork.monitor.service.data.service.plugin.ServerService
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.*

@Component
class ConnectedHandler @Autowired constructor(private val serverService: ServerService, private val sessionService: SessionService, private val discordService: DiscordService): EventHandlerImpl() {

    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)
    private val gson = GsonBuilder().create()

    /**
     * This will add the socket session and save the server information. Alert
     * will be sent to the discord.
     *
     * @param session           the web socket session
     * @param event             the event object.
     */
    override fun onEvent(session: WebSocketSession, event: Event): Mono<WebSocketMessage> {
        val info = gson.fromJson(event.value, ServerInfo::class.java)
        return sessionService.insert(session)
            .flatMap {
                if (info.id.isBlank())
                    return@flatMap serverService.insert(Server(index = 0))
                        .flatMap {
                            info.id = it.id.toString()
                            sendEvent(session, "connected_event", info)
                        }

                return@flatMap serverService.findById(UUID.fromString(info.id))
                    .flatMap { sendEvent(session, "connected_event", info) }
            }
            .doOnSuccess {
                logger.info("Client connected: \nSession Id: ${session.id} \nServer Id: ${info.id}")
                discordService.sendAlert("Server connected: \nSession ID: ${session.id} \nServer ID: ${info.id}")
            }
    }
}