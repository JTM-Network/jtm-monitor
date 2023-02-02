package com.jtmnetwork.monitor.core.domain.model.serverinfo

import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import jakarta.persistence.*
import org.bukkit.Server

data class SpigotServerInfo(val id: String = "", val name: String, val version: String, val bukkitVersion: String, val minecraftVersion: String, val maxPlayers: Int, val port: Int, val viewDistance: Int, val created: Long = System.currentTimeMillis()) {

    constructor(serverConfiguration: ServerConfiguration, server: Server): this(serverConfiguration.getServerId(), server.name, server.version, server.bukkitVersion, server.minecraftVersion, server.maxPlayers, server.port, server.viewDistance)
}
