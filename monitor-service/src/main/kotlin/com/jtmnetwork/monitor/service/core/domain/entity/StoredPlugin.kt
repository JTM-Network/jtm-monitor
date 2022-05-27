package com.jtmnetwork.monitor.service.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("plugins")
data class StoredPlugin(@Id val id: UUID = UUID.randomUUID(), val name: String, val version: String, val path: String, val uploaded: Long = System.currentTimeMillis())