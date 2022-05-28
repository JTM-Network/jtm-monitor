package com.jtmnetwork.monitor.entrypoint.log

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtmnetwork.monitor.core.domain.model.ServerLog
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.layout.PatternLayout
import org.slf4j.LoggerFactory
import java.util.*
import java.util.logging.Handler
import java.util.logging.LogRecord

@Singleton
class LogAppender @Inject constructor(private val connection: MonitorConnection): AbstractAppender("MonitorLogAppender", null, PatternLayout.newBuilder().withPattern("[%d{HH:mm:ss} %level]: %msg").build()) {

    private val logger = LoggerFactory.getLogger(LogAppender::class.java)

    override fun append(event: LogEvent) {
        val list: LinkedList<String> = LinkedList()
        list.add(event.message.format)
        connection.sendEvent("incoming_log_event", ServerLog(list))
    }
}