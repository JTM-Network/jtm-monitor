package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.usecase.file.PluginFileHandler
import com.jtmnetwork.monitor.service.data.repository.DownloadLogRepository
import com.jtmnetwork.monitor.service.data.repository.StoredPluginRepository
import com.jtmnetwork.monitor.service.data.service.StoredPluginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class DownloadService @Autowired constructor(private val logRepository: DownloadLogRepository, private val pluginRepository: StoredPluginRepository, private val fileHandler: PluginFileHandler) {

    fun downloadPlugin(server: String, pluginId: String): Mono<Resource> {
        return pluginRepository.findById(UUID.fromString(pluginId))
                .flatMap { fileHandler.fetch(it.path) }
    }
}