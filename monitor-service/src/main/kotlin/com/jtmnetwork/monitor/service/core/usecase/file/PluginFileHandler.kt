package com.jtmnetwork.monitor.service.core.usecase.file

import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono

class PluginFileHandler {

    private val path = "/plugins"

    fun save(filePart: FilePart): Mono<Void> {
        return Mono.empty()
    }

    fun fetch(name: String, version: String): Mono<Resource> {
        return Mono.empty()
    }

    fun fetchPlugins(): Mono<String> {
        return Mono.empty()
    }

    fun fetchVersions(name: String): Mono<String> {
        return Mono.empty()
    }

    fun deletePlugin(name: String): Mono<Void> {
        return Mono.empty()
    }
}