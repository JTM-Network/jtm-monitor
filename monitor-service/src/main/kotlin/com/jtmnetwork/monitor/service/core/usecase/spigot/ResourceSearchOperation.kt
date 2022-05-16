package com.jtmnetwork.monitor.service.core.usecase.spigot

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jtmnetwork.monitor.service.core.domain.model.OperationEvent
import com.jtmnetwork.monitor.service.core.domain.model.spigot.SpigotResource
import com.jtmnetwork.monitor.service.core.usecase.operation.Operation
import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import okhttp3.OkHttpClient
import okhttp3.Request

class ResourceSearchOperation(private val query: String): Operation {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val client = OkHttpClient().newBuilder().build()

    override fun execute(executor: OperationExecutor) {
        val request = Request.Builder().url("https://api.spiget.org/v2/search/resources/$query").build()
        val res = client.newCall(request).execute()
        if (!res.isSuccessful) executor.sendMessage(OperationEvent(name = "Error: ${res.code()}", value = res.message()))
        val body = res.body()?.string() ?: return
        val list: List<SpigotResource> = gson.fromJson(body, object : TypeToken<List<SpigotResource>>(){}.type)
        executor.sendMessage(OperationEvent("Search completed.", list))
    }
}