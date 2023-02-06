package com.jtmnetwork.monitor.entrypoint.log

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtmnetwork.monitor.core.domain.model.ServerLog
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnection
import com.jtmnetwork.monitor.entrypoint.socket.SpigotMonitorConnection
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.layout.PatternLayout
import java.util.*

@Singleton
class LogAppender @Inject constructor(private val connection: MonitorConnection): AbstractAppender("MonitorLogAppender", null, PatternLayout.newBuilder().withPattern("[%d{HH:mm:ss} %level]: %msg").build(), true, arrayOf()) {

    init {
        this.start()
    }

    override fun append(event: LogEvent) {
        val list: LinkedList<String> = LinkedList()
        list.add(event.message.format)
        connection.sendEvent("incoming_log_event", ServerLog(list))
    }
}