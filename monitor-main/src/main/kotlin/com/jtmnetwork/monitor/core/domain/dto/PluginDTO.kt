package com.jtmnetwork.monitor.core.domain.dto

import com.jtmnetwork.monitor.core.domain.model.Plugin
import java.util.*

data class PluginDTO(val id: UUID, val plugins: Map<String, Plugin>)