package com.jtmnetwork.monitor.core.usecase.log

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.log.LogAppender
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*


class LogReporter @Inject constructor(framework: Framework, private val logAppender: LogAppender) {

    private val logger = LoggerFactory.getLogger(LogReporter::class.java)
    private val file = File("${framework.server.worldContainer.path}/logs/latest.log")

    fun init() {
        val logger = LogManager.getRootLogger() as Logger
        logger.addAppender(logAppender)
    }

    fun getLogs(): LinkedList<String> {
        val list: LinkedList<String> = LinkedList()

        file.bufferedReader().readLines().forEach { list.add(it) }

        return list
    }
}