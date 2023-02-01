package com.jtmnetwork.monitor.core.usecase.handler

import com.google.gson.Gson
import com.jtmnetwork.monitor.core.domain.model.Event
import okhttp3.WebSocket

interface EventHandler {

    fun getName(): String

    fun getGson(): Gson

    fun onEvent(socket: WebSocket, event: Event)

    fun sendEvent(socket: WebSocket, name: String, value: Any)

    fun sendEvent(socket: WebSocket, value: Any)
}