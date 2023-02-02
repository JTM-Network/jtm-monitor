package com.jtmnetwork.monitor.service.core.usecase.spigot

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.dto.InstallRequestDTO
import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.core.domain.model.OperationEvent
import com.jtmnetwork.monitor.service.core.domain.model.spigot.SpigotInstallRequest
import com.jtmnetwork.monitor.service.core.usecase.operation.Operation
import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import okhttp3.OkHttpClient
import okhttp3.Request

class ResourceInstallOperation(private val sessionService: SessionService, private val pluginService: StoredPluginService, private val installRequest: SpigotInstallRequest, val client: OkHttpClient = OkHttpClient().newBuilder().build()): Operation {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun execute(executor: OperationExecutor) {
        // download the file if not found in stored plugins
        val localPlugin = findPluginLocally(installRequest.name, installRequest.version) ?: downloadSpigotPlugin(executor, installRequest.pluginId, installRequest.name, installRequest.version)
        if (localPlugin == null) {
            executor.sendMessage(OperationEvent(name = "Failed to find plugin", value = "Failed to find plugin"))
            return
        }
        // find the server session to install the server to

        // send the link to download it from this server
        // create a progress sink for the operation.
        // add a complete event handler
    }

    private fun findPluginLocally(name: String, version: String): StoredPlugin? {
        return pluginService.getPlugin(name, version).block()
    }

    private fun downloadSpigotPlugin(executor: OperationExecutor, pluginId: String, name: String, version: String): StoredPlugin? {
        val req = Request.Builder().url("https://api.spiget.org/v2/resources/${pluginId}/download").build()
        val res = client.newCall(req).execute()
        if (!res.isSuccessful) executor.sendMessage(OperationEvent(name = "Error: ${res.code()}", value = res.message()))
        val body = res.body() ?: return null
        val plugin = pluginService.insertPlugin(name, version, body.bytes()).block() ?: return null
        sendInstallRequest(installRequest, plugin)
        return null
    }

    private fun sendInstallRequest(request: SpigotInstallRequest, plugin: StoredPlugin) {
        val session = sessionService.findById(request.server).block() ?: return
        session.sendEvent("install_request_event", InstallRequestDTO("http://local.jtm-network.com/download/${request.server}/${plugin.id.toString()}"))
    }

    private fun sendInstallRequestEvent() {}
}