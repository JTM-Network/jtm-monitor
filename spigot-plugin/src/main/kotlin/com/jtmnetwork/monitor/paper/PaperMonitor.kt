package com.jtmnetwork.monitor.paper

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class PaperMonitor: Framework(false) {

    private val type = ServerType.PAPER

    override fun setup() {
        JTMMonitor.setup(type)
    }

    override fun init() {
        JTMMonitor.init(type)
    }

    override fun enable() {
        JTMMonitor.enable(type)
    }

    override fun disable() {
        JTMMonitor.disable(type)
    }
}