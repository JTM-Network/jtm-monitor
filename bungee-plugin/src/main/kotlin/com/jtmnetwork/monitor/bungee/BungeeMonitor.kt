package com.jtmnetwork.monitor.bungee

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class BungeeMonitor: Framework(false) {

    override fun setup() {
        TODO("Not yet implemented")
    }

    override fun init() {}

    override fun enable() {
        JTMMonitor.startup(ServerType.BUNGEE)
    }

    override fun disable() {

    }
}