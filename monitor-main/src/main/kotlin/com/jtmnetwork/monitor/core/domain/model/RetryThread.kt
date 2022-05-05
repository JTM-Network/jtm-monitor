package com.jtmnetwork.monitor.core.domain.model

import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection

class RetryThread(private val connection: MonitorConnection): Thread() {

    private var sleepStartTime: Long = 0

    override fun run() {
        if (isSleeping()) return
        connection.retry.perform()
    }

    fun setSleep() {
        this.sleepStartTime = System.currentTimeMillis()
    }

    fun isSleeping(): Boolean {
        return (this.sleepStartTime + (connection.retry.getDelay()) < System.currentTimeMillis())
    }
}