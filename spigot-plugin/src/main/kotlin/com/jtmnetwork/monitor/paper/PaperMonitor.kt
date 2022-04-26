package com.jtmnetwork.monitor.paper

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class PaperMonitor: Framework(false) {

    override fun setup() {}

    override fun init() {}

    override fun enable() {
        JTMMonitor.startup(ServerType.PAPER)
    }

    override fun disable() {}
}