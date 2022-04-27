package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.usecase.event.EventHandler
import com.jtmnetwork.monitor.service.core.usecase.event.impl.ConnectedHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class EventRepository @Autowired constructor(private val context: ApplicationContext) {

    private val map: MutableMap<String, EventHandler> = HashMap()

    @PostConstruct
    fun init() {
        insertHandler("connected_event", context.getBean(ConnectedHandler::class.java))
    }

    private fun insertHandler(name: String, handler: EventHandler): Boolean {
        if (map.containsKey(name)) return false
        map[name] = handler
        return true
    }

    fun getHandler(name: String): EventHandler? {
        return map[name]
    }

    fun removeHandler(name: String): EventHandler? {
        return map.remove(name)
    }
}