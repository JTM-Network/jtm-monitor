package com.jtmnetwork.monitor.service.entrypoint.controller

import com.jtmnetwork.monitor.service.core.domain.model.CommandDTO
import com.jtmnetwork.monitor.service.data.service.plugin.CommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/command")
class CommandController @Autowired constructor(private val commandService: CommandService) {

    @PostMapping("/spigot")
    fun sendSpigotCommand(@RequestBody dto: CommandDTO): Mono<Void> {
        return commandService.sendSpigotCommand(dto)
    }
}