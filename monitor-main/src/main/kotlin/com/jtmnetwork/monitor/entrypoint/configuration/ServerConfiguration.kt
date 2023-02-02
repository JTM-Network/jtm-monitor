package com.jtmnetwork.monitor.entrypoint.configuration

interface ServerConfiguration {

    fun getServerId(): String

    fun setServerId(id: String)
}