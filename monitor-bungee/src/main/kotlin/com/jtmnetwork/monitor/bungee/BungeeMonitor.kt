package com.jtmnetwork.monitor.bungee

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class BungeeMonitor: Framework(false) {

    private val type = ServerType.BUNGEE

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