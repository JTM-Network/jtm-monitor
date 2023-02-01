package com.jtmnetwork.monitor.core.util

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginManager
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class TestUtil {
    companion object {
        fun createConnection(framework: Framework): MonitorConnection {
            val connection: MonitorConnection = mock()

            `when`(connection.framework).thenReturn(framework)

            return connection
        }

        fun createFramework(pluginManager: PluginManager): Framework {
            val framework: Framework = mock()
            val server: Server = mock()

            `when`(framework.server).thenReturn(server)
            `when`(server.pluginManager).thenReturn(pluginManager)

            return framework
        }

        fun createPluginManager(): PluginManager {
            val pluginManager: PluginManager = mock()
            val plugin: Plugin = mock()
            val descriptionFile: PluginDescriptionFile = mock()

            `when`(plugin.name).thenReturn("JTMMonitor")
            `when`(plugin.description).thenReturn(descriptionFile)
            `when`(descriptionFile.version).thenReturn("1.0")
            `when`(descriptionFile.description).thenReturn("Description")
            `when`(pluginManager.plugins).thenReturn(arrayOf(plugin))

            return pluginManager
        }
    }
}