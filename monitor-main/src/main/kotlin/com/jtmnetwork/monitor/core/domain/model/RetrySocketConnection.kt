package com.jtmnetwork.monitor.core.domain.model

import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import okhttp3.WebSocket
import java.util.concurrent.atomic.AtomicInteger

data class RetrySocketConnection(private val connection: MonitorConnection, private val maxAttempts: Int = 10, private val delay: Long = 5000, private val attempts: AtomicInteger = AtomicInteger(), private val errors: MutableList<Throwable> = ArrayList()) {

    fun perform(): WebSocket? {
        do {
            if (!connection.isConnected()) connection.connect()
        } while (true)
    }

    fun addError(throwable: Throwable) {
        this.errors.add(throwable)
    }

    fun reachedMaxAttempts(): Boolean {
        return this.attempts.incrementAndGet() >= this.maxAttempts
    }

    fun getDelay(): Long {
        return this.delay
    }
}