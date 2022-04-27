package com.jtmnetwork.monitor.service.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
open class TestDatabaseConfiguration {

    lateinit var redisServer: RedisServer

    var port: Int = 6370

    @PostConstruct
    fun init() {
        redisServer = RedisServer(port)
        redisServer.start()
    }

    @PreDestroy
    fun destroy() {
        redisServer.stop()
    }
}