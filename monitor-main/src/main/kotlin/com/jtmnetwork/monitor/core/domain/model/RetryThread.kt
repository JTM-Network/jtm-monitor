package com.jtmnetwork.monitor.core.domain.model

import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection

class RetryThread(private val connection: MonitorConnection): Thread() {

    private var sleepEndTime: Long = 0

    override fun run() {
        if (isSleeping()) return
        if (connection.isTryingToConnect()) return
        connection.retry.perform()
    }

    fun setSleep() {
        this.sleepEndTime = (System.currentTimeMillis() + connection.retry.getDelay())
    }

    fun isSleeping(): Boolean {
        return this.sleepEndTime > System.currentTimeMillis()
    }
}