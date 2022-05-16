package com.jtmnetwork.monitor.service.entrypoint.configuration

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DiscordConfiguration {

    @Value("\${discord.api:key}")
    lateinit var key: String

    @Bean
    open fun discordBot(): JDA {
        val builder = JDABuilder.createDefault(key)
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        builder.setBulkDeleteSplittingEnabled(false)
        builder.setCompression(Compression.NONE)
        builder.setActivity(Activity.playing("Monitor"))
        return builder.build()
    }
}