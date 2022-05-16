package com.jtmnetwork.monitor.service.core.usecase.server

import com.jtmnetwork.monitor.service.core.domain.entity.Server
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ServerRepository: ReactiveMongoRepository<Server, UUID> {
}