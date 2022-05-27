package com.jtmnetwork.monitor

import com.google.inject.Guice
import com.google.inject.Injector
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.data.repository.EventRepository
import com.jtmnetwork.monitor.entrypoint.event.EventRegistry
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.module.EventModule
import com.jtmnetwork.monitor.module.MonitorModule

class JTMMonitor {
    companion object {
        private lateinit var injector: Injector
        private lateinit var registry: EventRegistry

        fun setup(framework: Framework) {
            injector = framework.injector(listOf(MonitorModule(framework), EventModule()))
            registry = EventRegistry(getEventRepository(), injector)
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