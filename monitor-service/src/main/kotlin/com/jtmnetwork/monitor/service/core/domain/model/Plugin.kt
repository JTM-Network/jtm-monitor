package com.jtmnetwork.monitor.service.core.domain.model

data class Plugin(val name: String, val version: String, var enabled: Boolean) {

    fun enabled() {
        this.enabled = true
    }

    fun disabled() {
        this.enabled = false
    }
}
