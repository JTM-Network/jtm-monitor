package com.jtmnetwork.monitor.service.data.operation

import com.google.gson.GsonBuilder
import com.jtmnetwork.monitor.service.core.usecase.operation.Operation
import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap

@Component
class OperationObserver {

    private val logger = LoggerFactory.getLogger(OperationObserver::class.java)
    private val executor = Executors.newFixedThreadPool(10)
    private val sinks: MutableMap<UUID, Sinks.Many<String>> = HashMap()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun addOperation(op: Operation): Sinks.Many<String>? {
        val opExecutor = OperationExecutor(UUID.randomUUID(), this, op)
        val id = opExecutor.id
        val sink = addSink(id)
        executor.submit(opExecutor)
        return sink
    }

    fun sendMessage(id: UUID, message: String) {
        val sink = getOpSink(id) ?: return
        val result = sink.tryEmitNext(message)
        if (result.isFailure) {
            logger.error("Failed to send message on sink: ${id.toString()}")
            return
        }
        logger.info("Successfully sent message: $id")
    }

    fun addSink(id: UUID): Sinks.Many<String>? {
        this.sinks[id] = Sinks.many().replay().all()
        return this.sinks[id]
    }

    private fun getOpSink(id: UUID): Sinks.Many<String>? {
        return this.sinks[id]
    }

    fun removeOpSink(id: UUID) {
        this.sinks.remove(id)
    }
}