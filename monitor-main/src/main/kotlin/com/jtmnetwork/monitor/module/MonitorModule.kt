package com.jtmnetwork.monitor.module

import com.google.inject.AbstractModule
import com.jtmnetwork.monitor.core.domain.constants.ServerType
import com.jtmnetwork.monitor.core.usecase.log.LogReporter
import com.jtmnetwork.monitor.core.usecase.log.SpigotLogReporter
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.configuration.SpigotServerConfiguration
import com.jtmnetwork.monitor.entrypoint.log.LogAppender
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.entrypoint.socket.SpigotMonitorConnection

class MonitorModule(private val type: ServerType): AbstractModule() {
    override fun configure() {
        bind(LogAppender::class.java)

        when(type) {
            ServerType.SPIGOT -> {
                bind(MonitorConnection::class.java).to(SpigotMonitorConnection::class.java)
                bind(ServerConfiguration::class.java).to(SpigotServerConfiguration::class.java)
                bind(LogReporter::class.java).to(SpigotLogReporter::class.java)
            }

            ServerType.BUNGEE -> {}

            ServerType.VELOCITY -> {}
        }
    }
}