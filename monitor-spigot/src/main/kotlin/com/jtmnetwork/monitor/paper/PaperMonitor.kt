package com.jtmnetwork.monitor.paper

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class PaperMonitor: Framework(false) {

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