package com.jtmnetwork.monitor.module

import com.google.inject.AbstractModule
import com.jtmnetwork.monitor.core.domain.constants.ServerType
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import com.jtmnetwork.monitor.entrypoint.handler.SpigotCommandHandler
import com.jtmnetwork.monitor.entrypoint.handler.SpigotConnectedHandler

class EventModule(private val type: ServerType): AbstractModule() {
    override fun configure() {
        bind(EventDispatcher::class.java)
        bind(EventRepository::class.java)

        when(type) {
            ServerType.SPIGOT -> {
                bind(SpigotConnectedHandler::class.java)
                bind(SpigotCommandHandler::class.java)
            }

            ServerType.BUNGEE -> {}

            ServerType.VELOCITY -> {}
        }

    }
}