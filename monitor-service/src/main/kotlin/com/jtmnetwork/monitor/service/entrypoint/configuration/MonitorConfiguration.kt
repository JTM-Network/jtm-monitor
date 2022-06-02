package com.jtmnetwork.monitor.service.entrypoint.configuration

import com.jtmnetwork.monitor.service.entrypoint.socket.MonitorSocketHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

@Configuration
open class MonitorConfiguration {

    @Autowired
    lateinit var socketHandler: MonitorSocketHandler

    /**
     * Setup minecraft server monitor socket handler.
     */
    @Bean
    open fun handlerMapping(): HandlerMapping {
        val map: MutableMap<String, WebSocketHandler> = HashMap()
        map["/monitor"] = socketHandler

        val mapping = SimpleUrlHandlerMapping()
        mapping.urlMap = map
        mapping.order = Ordered.HIGHEST_PRECEDENCE
        return mapping
    }
}