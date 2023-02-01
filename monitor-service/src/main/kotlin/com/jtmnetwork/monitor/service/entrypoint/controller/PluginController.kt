package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.dto.PluginRequestDTO
import com.jtmnetwork.monitor.service.data.service.plugin.CommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/plugin")
class PluginController @Autowired constructor(private val commandService: CommandService) {

    @PostMapping("/enable")
    fun enablePlugin(@RequestBody dto: PluginRequestDTO): Mono<Void> = commandService.sendEnablePlugin(dto)

    @PostMapping("/disable")
    fun disablePlugin(@RequestBody dto: PluginRequestDTO): Mono<Void> = commandService.sendDisablePlugin(dto)
}