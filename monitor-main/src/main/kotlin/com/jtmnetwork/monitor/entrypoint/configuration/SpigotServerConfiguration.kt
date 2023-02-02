package com.jtmnetwork.monitor.entrypoint.configuration

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtm.framework.core.usecase.configuration.ConfigurationImpl

class SpigotServerConfiguration @Inject constructor(framework: Framework): ServerConfiguration, ConfigurationImpl(framework, framework.dataFolder.path, "", "server") {

    override fun getServerId(): String {
        val config = getConfig() ?: return ""
        return config.getString("server_id") ?: return ""
    }

    override fun setServerId(id: String) {
        val config = getConfig() ?: return
        config.set("server_id", id)
        saveConfig()
    }
}