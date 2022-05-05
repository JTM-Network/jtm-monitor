package com.jtmnetwork.monitor.core.domain.model

import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import org.slf4j.LoggerFactory

class RetryThread(private val connection: MonitorConnection): Thread() {

    private val logger = LoggerFactory.getLogger(RetryThread::class.java)
    private var sleepEndTime: Long = 0

    override fun run() {
        do {
            logger.info("Sleep check.")
            if (isSleeping()) return
            logger.info("Connect check...")
            if (connection.isTryingToConnect()) return
            logger.info("Perform task.")
            connection.retry.perform()
        } while (true)
    }

    fun setSleep() {
        this.sleepEndTime = (System.currentTimeMillis() + connection.retry.getDelay())
    }

    fun isSleeping(): Boolean {
        return this.sleepEndTime > System.currentTimeMillis()
    }
}