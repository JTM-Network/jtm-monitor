package com.jtmnetwork.monitor.service.core.usecase.spigot

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import com.jtmnetwork.monitor.service.core.domain.model.OperationEvent
import com.jtmnetwork.monitor.service.core.domain.model.spigot.SpigotInstallRequest
import com.jtmnetwork.monitor.service.core.usecase.operation.Operation
import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import com.jtmnetwork.monitor.service.data.service.plugin.SessionService
import okhttp3.OkHttpClient
import okhttp3.Request

class ResourceInstallOperation(private val sessionService: SessionService, private val pluginService: StoredPluginService, private val installRequest: SpigotInstallRequest): Operation {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val client = OkHttpClient().newBuilder().build()

    override fun execute(executor: OperationExecutor) {

    }

    private fun findPluginLocally(name: String, version: String): StoredPlugin? {
        return pluginService.getPlugin(name, version).block()
    }

    private fun downloadSpigotPlugin(executor: OperationExecutor, pluginId: String): StoredPlugin? {
        val req = Request.Builder().url("https://api.spiget.org/v2/resources/${pluginId}/download").build()
        val res = client.newCall(req).execute()
        if (!res.isSuccessful) executor.sendMessage(OperationEvent(name = "Error: ${res.code()}", value = res.message()))
        return null
    }

    private fun sendInstallRequest(request: SpigotInstallRequest) {
        val session = sessionService.findById(request.server).block()
    }

    private fun sendInstallRequestEvent() {}
}