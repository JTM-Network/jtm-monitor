package com.jtmnetwork.monitor.service.core.usecase.discord.request

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel
import org.slf4j.LoggerFactory

class DefaultRequester(private val jda: JDA): Requester {

    private val logger = LoggerFactory.getLogger(DefaultRequester::class.java)

    override fun sendMessage(message: String) {
        initChannel().sendMessage(message).queue {
            logger.info("Successfully sent message.")
        }
    }

    override fun sendAlert(message: String) {
        initChannel().sendMessage(message).queue {
            logger.info("Successfully sent message.")
        }
    }

    private fun initChannel(): TextChannel {
        val guilds = jda.guilds
        val guild = guilds[0]
        val channels = guild.getTextChannelsByName("monitoring", true)
        return channels[0]
    }
}