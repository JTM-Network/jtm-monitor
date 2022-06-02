package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.domain.entity.StoredPlugin
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DownloadLogRepository: ReactiveMongoRepository<StoredPlugin, Int>