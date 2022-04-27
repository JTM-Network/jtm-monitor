package com.jtmnetwork.main

import com.google.inject.Guice
import com.google.inject.Injector
import com.jtm.framework.Framework
import com.jtmnetwork.main.entrypoint.handler.ConnectedHandler
import com.jtmnetwork.main.entrypoint.module.MonitorModule
import com.jtmnetwork.main.entrypoint.socket.MonitorConnection

class JTMMonitor {
    companion object {
        private lateinit var injector: Injector

        fun setup() {

        }

        fun setup(framework: Framework) {
            injector = Guice.createInjector(MonitorModule(framework))
        }

        fun init() {
            injector.getInstance(ConnectedHandler::class.java).init()
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
    }
}