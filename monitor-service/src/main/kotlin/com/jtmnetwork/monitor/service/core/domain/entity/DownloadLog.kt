package com.jtmnetwork.monitor.service.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("download_plugin_logs")
data class DownloadLog(@Id val id: Int, val pluginId: UUID, val timestamp: Long = System.currentTimeMillis())
