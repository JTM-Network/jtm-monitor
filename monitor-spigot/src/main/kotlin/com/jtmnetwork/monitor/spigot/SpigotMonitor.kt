package com.jtmnetwork.monitor.spigot

import com.jtm.framework.Framework
import com.jtmnetwork.monitor.JTMMonitor

class SpigotMonitor: Framework(false) {

    override fun setup() {
        JTMMonitor.spigotSetup(this)
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