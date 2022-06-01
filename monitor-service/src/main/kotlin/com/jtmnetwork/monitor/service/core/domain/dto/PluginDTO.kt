package com.jtmnetwork.monitor.service.core.domain.dto

import com.jtmnetwork.monitor.service.core.domain.model.Plugin
import java.util.UUID

data class PluginDTO(val id: UUID, val plugins: Map<String, Plugin>)
