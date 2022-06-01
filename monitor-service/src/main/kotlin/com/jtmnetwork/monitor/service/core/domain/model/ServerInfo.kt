package com.jtmnetwork.monitor.service.core.domain.model

data class ServerInfo(var id: String = "", val name: String, val version: String, val bukkitVersion: String, val minecraftVersion: String, val maxPlayers: Int, val port: Int, val viewDistance: Int, val created: Long)