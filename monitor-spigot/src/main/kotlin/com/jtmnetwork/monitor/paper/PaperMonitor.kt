package com.jtmnetwork.monitor.paper

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.JTMMonitor
import org.apache.logging.log4j.LogManager

class PaperMonitor: Framework(false) {

    private val logger = LogManager.getRootLogger()

    override fun setup() {
        JTMMonitor.setup(this)
    }

    override fun init() {
        JTMMonitor.init()
    }

    override fun enable() {
        JTMMonitor.enable()
    }

    override fun disable() {
        JTMMonitor.disable()
    }
}