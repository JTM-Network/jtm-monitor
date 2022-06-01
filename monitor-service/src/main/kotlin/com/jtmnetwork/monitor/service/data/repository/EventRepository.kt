package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.usecase.event.EventHandler
import com.jtmnetwork.monitor.service.entrypoint.handler.ConnectedHandler
import com.jtmnetwork.monitor.service.entrypoint.handler.log.IncomingLogHandler
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.DisablePluginHandler
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.EnablePluginHandler
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.UpdatePluginHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Store Event handlers here.
 */
@Component
class EventRepository @Autowired constructor(private val context: ApplicationContext) {

    private val map: MutableMap<String, EventHandler> = HashMap()

    @PostConstruct
    fun init() {
        insertHandler(context.getBean(ConnectedHandler::class.java))
        insertHandler(context.getBean(IncomingLogHandler::class.java))
        insertHandler(context.getBean(UpdatePluginHandler::class.java))
        insertHandler(context.getBean(EnablePluginHandler::class.java))
        insertHandler(context.getBean(DisablePluginHandler::class.java))
    }

    private fun insertHandler(handler: EventHandler): Boolean {
        if (map.containsKey(handler.getName())) return false
        map[handler.getName()] = handler
        return true
    }

    fun getHandler(name: String): EventHandler? {
        return map[name]
    }

    fun removeHandler(name: String): EventHandler? {
        return map.remove(name)
    }
}