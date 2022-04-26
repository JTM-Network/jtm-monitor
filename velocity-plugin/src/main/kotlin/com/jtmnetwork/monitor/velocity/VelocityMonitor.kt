package com.jtmnetwork.monitor.velocity

import com.jtm.framework.Framework
import com.jtmnetwork.main.JTMMonitor
import com.jtmnetwork.main.core.domain.constants.ServerType

class VelocityMonitor: Framework(false) {

    private val type = ServerType.VELOCITY

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