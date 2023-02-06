package com.jtmnetwork.monitor.core.usecase.log

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.log.LogAppender
import org.apache.logging.log4j.core.Logger
import java.io.File
import java.util.*

class SpigotLogReporter @Inject constructor(val framework: Framework, private val logAppender: LogAppender): LogReporter {

    private val file = File("${framework.server.worldContainer.path}/logs/latest.log")

    override fun init() {
        val logger = framework.log4JLogger as Logger
        logger.addAppender(logAppender)
    }

    override fun getLogs(): LinkedList<String> {
        val list: LinkedList<String> = LinkedList()
        file.bufferedReader().readLines().forEach { list.add(it) }
        return list
    }
}