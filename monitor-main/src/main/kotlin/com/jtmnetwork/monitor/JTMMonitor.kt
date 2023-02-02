package com.jtmnetwork.monitor

import com.google.inject.Guice
import com.google.inject.Injector
import com.jtm.framework.Framework
import com.jtm.framework.core.util.Logging
import com.jtmnetwork.monitor.core.domain.constants.ServerType
import com.jtmnetwork.monitor.core.domain.model.serverinfo.SpigotServerInfo
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.event.EventRegistry
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.entrypoint.socket.SpigotMonitorConnection
import com.jtmnetwork.monitor.module.EventModule
import com.jtmnetwork.monitor.module.MonitorModule

class JTMMonitor {
    companion object {
        private lateinit var injector: Injector
        private lateinit var registry: EventRegistry
        private lateinit var type: ServerType

        fun spigotSetup(framework: Framework) {
            type = ServerType.SPIGOT
            injector = framework.injector(listOf(MonitorModule(type), EventModule(type)))
            registry = EventRegistry(getEventRepository(), injector, type)
        }

        fun bungeeSetup() {
            type = ServerType.BUNGEE
            injector = Guice.createInjector(MonitorModule(type), EventModule(type))
        }

        fun velocitySetup() {
            type = ServerType.VELOCITY
            injector = Guice.createInjector(MonitorModule(type), EventModule(type))
        }

        fun init() {
            registry.init()
        }

        fun enable() {
            getMonitorConnection().connect()
        }

        fun disable() {
            getMonitorConnection().disconnect()
        }

        private fun getMonitorConnection(): MonitorConnection {
            return injector.getInstance(MonitorConnection::class.java)
        }

        private fun getEventRepository(): EventRepository {
            return injector.getInstance(EventRepository::class.java)
        }
    }
}