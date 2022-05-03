package com.jtmnetwork.main.core.usecase.handler

import com.jtmnetwork.main.core.domain.model.Event
import okhttp3.WebSocket

interface EventHandler {

    fun onEvent(socket: WebSocket, event: Event)

    fun sendEvent(socket: WebSocket, name: String, value: Any)
}