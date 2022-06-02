package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.usecase.discord.request.DefaultRequester
import net.dv8tion.jda.api.JDA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DiscordService @Autowired constructor(jda: JDA) {

    private val requester = DefaultRequester(jda)

    /**
     * Send a message through the discord bot.
     *
     * @param message                   the message
     */
    fun sendMessage(message: String) {
        requester.sendMessage(message)
    }

    /**
     * Send an alert to discord.
     *
     * @param message                   the message
     */
    fun sendAlert(message: String) {
        requester.sendAlert(message)
    }
}