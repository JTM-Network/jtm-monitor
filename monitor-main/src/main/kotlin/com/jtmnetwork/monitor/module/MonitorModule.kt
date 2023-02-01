package com.jtmnetwork.monitor.module

import com.google.inject.AbstractModule
import com.jtmnetwork.monitor.core.usecase.log.LogReporter
import com.jtmnetwork.monitor.data.service.ServerInfoService
import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import com.jtmnetwork.monitor.entrypoint.log.LogAppender
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection

class MonitorModule(): AbstractModule() {
    override fun configure() {
        bind(MonitorConnection::class.java)

        bind(ServerConfiguration::class.java)

        bind(ServerInfoService::class.java)
        bind(LogAppender::class.java)
        bind(LogReporter::class.java)
    }
}