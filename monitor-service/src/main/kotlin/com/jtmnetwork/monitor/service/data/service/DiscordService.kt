package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.usecase.discord.request.DefaultRequester
import net.dv8tion.jda.api.JDA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DiscordService @Autowired constructor(jda: JDA) {

    private val requester = DefaultRequester(jda)

    fun sendMessage(message: String) {
        requester.sendMessage(message)
    }

    fun sendAlert(message: String) {
        requester.sendAlert(message)
    }
}