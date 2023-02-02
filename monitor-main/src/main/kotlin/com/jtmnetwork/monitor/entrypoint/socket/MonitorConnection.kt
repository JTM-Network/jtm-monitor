package com.jtmnetwork.monitor.entrypoint.socket

interface MonitorConnection {

    fun connect()

    fun disconnect()

    fun sendEvent(name: String, value: Any)
}