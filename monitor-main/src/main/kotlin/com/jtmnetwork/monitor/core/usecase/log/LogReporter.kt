package com.jtmnetwork.monitor.core.usecase.log

import com.google.inject.Inject
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.log.LogHandler
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*


class LogReporter @Inject constructor(private val framework: Framework, private val logHandler: LogHandler) {

    private val logger = LoggerFactory.getLogger(LogReporter::class.java)
    private val file = File("${framework.server.worldContainer.path}/logs/latest.log")

    fun init() {
        framework.server.logger.addHandler(logHandler)
    }

    fun getLogs(): LinkedList<String> {
        val list: LinkedList<String> = LinkedList()

        file.bufferedReader().readLines().forEach { list.add(it) }

        return list
    }
}