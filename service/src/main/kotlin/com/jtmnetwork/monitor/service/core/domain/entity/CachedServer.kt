package com.jtmnetwork.monitor.service.core.domain.entity

import org.springframework.data.redis.core.RedisHash

@RedisHash("servers")
data class CachedServer(val server: Server)
