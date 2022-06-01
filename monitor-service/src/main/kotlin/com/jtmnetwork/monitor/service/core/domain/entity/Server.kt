package com.jtmnetwork.monitor.service.core.domain.entity

import com.jtmnetwork.monitor.service.core.domain.model.Plugin
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.util.*

@Document("servers")
data class Server(@Id val id: UUID = UUID.randomUUID(), val index: Int, var plugins: Map<String, Plugin> = mapOf(), val created: Long = System.currentTimeMillis()): Serializable {

    fun update(server: Server): Server {
        return this
    }

    fun updatePlugins(plugins: Map<String, Plugin>): Server {
        this.plugins = plugins
        return this
    }

    fun enable(name: String): Server {
        val plugin = plugins[name] ?: return this
        plugin.enabled()
        return this
    }
    fun disable(name: String): Server {
        val plugin = plugins[name] ?: return this
        plugin.disabled()
        return this
    }
}
