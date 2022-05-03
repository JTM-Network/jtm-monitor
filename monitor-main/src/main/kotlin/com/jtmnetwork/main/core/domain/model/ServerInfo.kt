package com.jtmnetwork.main.core.domain.model

import com.jtmnetwork.main.entrypoint.configuration.ServerConfiguration
import org.bukkit.Server

data class ServerInfo(val id: String = "", val name: String, val version: String, val bukkitVersion: String, val minecraftVersion: String, val maxPlayers: Int, val port: Int, val viewDistance: Int) {

    constructor(serverConfiguration: ServerConfiguration, server: Server): this(serverConfiguration.getServerId(), server.name, server.version, server.bukkitVersion, server.minecraftVersion, server.maxPlayers, server.port, server.viewDistance)
}
