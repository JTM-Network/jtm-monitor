package com.jtmnetwork.main.entrypoint.configuration

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtm.framework.core.usecase.configuration.ConfigurationImpl

class ServerConfiguration @Inject constructor(framework: Framework): ConfigurationImpl(framework, framework.dataFolder.path, "", "server") {

    fun getServerId(): String {
        val config = getConfig() ?: return ""
        return config.getString("server_id") ?: return ""
    }

    fun setServerId(id: String) {
        val config = getConfig() ?: return
        config.set("server_id", id)
        saveConfig()
    }
}