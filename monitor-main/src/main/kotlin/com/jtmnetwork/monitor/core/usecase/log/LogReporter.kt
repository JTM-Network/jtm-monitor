package com.jtmnetwork.monitor.core.usecase.log

import java.util.*

interface LogReporter {

    fun init()

    fun getLogs(): LinkedList<String>
}