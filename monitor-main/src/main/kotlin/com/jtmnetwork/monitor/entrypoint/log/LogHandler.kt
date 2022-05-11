package com.jtmnetwork.monitor.entrypoint.log

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtmnetwork.monitor.core.domain.model.ServerLog
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import org.slf4j.LoggerFactory
import java.util.*
import java.util.logging.Handler
import java.util.logging.LogRecord

@Singleton
class LogHandler @Inject constructor(private val connection: MonitorConnection): Handler() {

    private val logger = LoggerFactory.getLogger(LogHandler::class.java)
    private val list: LinkedList<String> = LinkedList()

    override fun publish(record: LogRecord?) {
        val rec = record ?: return
        list.add(rec.message)

        connection.sendEvent("incoming_log_event", ServerLog(list))
    }

    override fun flush() {}

    override fun close() {}
}