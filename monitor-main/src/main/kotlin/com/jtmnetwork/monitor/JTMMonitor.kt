package com.jtmnetwork.monitor

import com.google.inject.Injector
import com.jtm.framework.Framework
import com.jtm.framework.core.util.Logging
import com.jtmnetwork.monitor.core.domain.constants.ServerType
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.event.EventRegistry
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.module.EventModule
import com.jtmnetwork.monitor.module.MonitorModule

class JTMMonitor {
    companion object {
        private lateinit var injector: Injector
        private lateinit var registry: EventRegistry
        private lateinit var type: ServerType

        fun spigotSetup(framework: Framework) {
            injector = framework.injector(listOf(MonitorModule(), EventModule()))
            registry = EventRegistry(getEventRepository(), injector)
            type = ServerType.SPIGOT

            framework.registerClass(ServerInfo::class.java)
        }

        fun bungeeSetup() {
            type = ServerType.BUNGEE
        }

        fun velocitySetup() {
            type = ServerType.VELOCITY
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

        private fun getLogging(): Logging {
            return injector.getInstance(Logging::class.java)
        }
    }
}