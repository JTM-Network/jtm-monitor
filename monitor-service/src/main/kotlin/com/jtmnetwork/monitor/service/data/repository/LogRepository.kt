package com.jtmnetwork.monitor.service.data.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class LogRepository {

    private val logger = LoggerFactory.getLogger(LogRepository::class.java)
    private val sinks: MutableMap<String, Sinks.Many<String>> = HashMap()

    fun addSink(id: String) {
        this.sinks[id] = Sinks.many().replay().all()
    }

    fun getSink(id: String): Sinks.Many<String>? {
        return this.sinks[id]
    }

    fun getSinks(): Flux<String> {
        return Flux.fromIterable(this.sinks.keys.toList())
    }

    fun sendMessage(id: String, msg: String) {
        val sink = getSink(id) ?: return
        val result = sink.tryEmitNext(msg)
        if (result.isFailure) {
            logger.info("Failed to send message")
            result.orThrow()
        }
    }

    fun removeSink(id: String) {
        this.sinks.remove(id)
    }
}