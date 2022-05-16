package com.jtmnetwork.monitor.service.core.usecase.operation

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import java.util.*

class OperationExecutor(val id: UUID = UUID.randomUUID(), val observer: OperationObserver, private val operation: Operation): Runnable {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun run() {
        operation.execute(this)
    }

    fun sendMessage(message: Any) {
        observer.sendMessage(id, gson.toJson(message))
    }
}