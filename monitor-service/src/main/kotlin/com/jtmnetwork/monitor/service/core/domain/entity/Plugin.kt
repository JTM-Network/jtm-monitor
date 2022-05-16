package com.jtmnetwork.monitor.service.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("plugins")
data class Plugin(@Id val id: UUID = UUID.randomUUID(), val name: String, val version: String, val uploaded: Long = System.currentTimeMillis())