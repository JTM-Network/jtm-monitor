package com.jtmnetwork.monitor

import com.google.inject.Injector
import com.jtm.framework.Framework
import com.jtmnetwork.monitor.entrypoint.module.MonitorModule

class JTMMonitor: Framework(false) {

    private lateinit var injector: Injector

    override fun setup() {
        injector = injector(listOf(MonitorModule()))
    }

    override fun init() {}

    override fun enable() {}

    override fun disable() {}


}