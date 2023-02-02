package com.jtmnetwork.monitor.entrypoint.event

import com.google.inject.Injector
import com.jtmnetwork.monitor.core.domain.constants.ServerType
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.handler.SpigotCommandHandler
import com.jtmnetwork.monitor.entrypoint.handler.SpigotConnectedHandler
import com.jtmnetwork.monitor.entrypoint.handler.UpdateResponseHandler

class EventRegistry(private val repository: EventRepository, private val injector: Injector, private val type: ServerType) {

    fun init() {
        when(type) {
            ServerType.SPIGOT -> {
                repository.registerHandler(injector.getInstance(SpigotConnectedHandler::class.java))
                repository.registerHandler(injector.getInstance(SpigotCommandHandler::class.java))
            }

            ServerType.BUNGEE -> {}
            ServerType.VELOCITY -> {}
        }

        repository.registerHandler(injector.getInstance(UpdateResponseHandler::class.java))
    }
}