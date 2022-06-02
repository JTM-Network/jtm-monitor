package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.data.service.plugin.DownloadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/download")
class DownloadController @Autowired constructor(private val downloadService: DownloadService) {

    @GetMapping("/{server}/{plugin}")
    fun downloadPlugin(@PathVariable server: String, @PathVariable plugin: String): Mono<Resource> {
        return Mono.empty()
    }
}