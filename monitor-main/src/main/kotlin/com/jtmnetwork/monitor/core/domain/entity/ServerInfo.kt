package com.jtmnetwork.monitor.core.domain.entity

import com.jtmnetwork.monitor.entrypoint.configuration.ServerConfiguration
import org.bukkit.Server
import javax.persistence.*

@Entity
@Table(name = "servers")
data class ServerInfo(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: String = "", val name: String, val version: String, val bukkitVersion: String, val minecraftVersion: String, val maxPlayers: Int, val port: Int, val viewDistance: Int, val created: Long = System.currentTimeMillis()) {

    constructor(serverConfiguration: ServerConfiguration, server: Server): this(serverConfiguration.getServerId(), server.name, server.version, server.bukkitVersion, server.minecraftVersion, server.maxPlayers, server.port, server.viewDistance)
}
