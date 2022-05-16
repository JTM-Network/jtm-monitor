package com.jtmnetwork.monitor.service.core.usecase.file

import com.jtmnetwork.monitor.service.core.domain.exception.plugin.FailedPluginDeletion
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.FailedVersionDeletion
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.FileNotFound
import com.jtmnetwork.monitor.service.core.domain.exception.plugin.NoFilesFound
import org.slf4j.LoggerFactory
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File

class PluginFileHandler {

    private val logger = LoggerFactory.getLogger(PluginFileHandler::class.java)
    var path = "/plugins"

    fun save(name: String, version: String, filePart: FilePart): Mono<String> {
        val folder = File("$path/$name")
        if (!folder.exists() && folder.mkdirs()) logger.info("Created directories at: ${folder.path}")
        val file = File("$path/$name", "$name-$version.jar")
        return filePart.transferTo(file)
            .map { "$path/$name/$name-$version.jar" }
    }

    fun fetch(path: String): Mono<Resource> {
        val file = File("${this.path}/$path")
        if (!file.exists()) return Mono.error { FileNotFound() }
        return Mono.just(FileSystemResource(file))
    }

    fun fetchPlugins(): Flux<String> {
        val folder = File(path)
        val list = folder.listFiles() ?: return Flux.error(NoFilesFound())
        return Flux.fromArray(list)
            .map { it.name }
    }

    fun fetchVersions(name: String): Flux<String> {
        val folder = File("$path/$name")
        val list = folder.listFiles() ?: return Flux.error(NoFilesFound())
        return Flux.fromArray(list)
            .map { it.name }
    }

    fun deletePlugin(name: String): Mono<String> {
        val folder = File("$path/$name")
        if (!folder.exists()) return Mono.error { NoFilesFound() }
        val success = folder.deleteRecursively()
        if (!success) return Mono.error { FailedPluginDeletion() }
        return Mono.just(folder.name)
    }

    fun deleteVersion(name: String, version: String): Mono<String> {
        val file = File("$path/$name/$name-$version.jar")
        if (!file.exists()) return Mono.error { FileNotFound() }
        val success = file.deleteRecursively()
        if (!success) return Mono.error { FailedVersionDeletion() }
        return Mono.just(file.name)
    }
}