package com.jtmnetwork.monitor.core.usecase.handler

import com.jtmnetwork.monitor.core.domain.model.Event
import okhttp3.WebSocket

interface EventHandler {

    fun onEvent(socket: WebSocket, event: Event)

    fun sendEvent(socket: WebSocket, name: String, value: Any)
}