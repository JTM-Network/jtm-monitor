package com.jtmnetwork.monitor.service.core.usecase.discord.request

interface Requester {

    fun sendMessage(message: String)

    fun sendAlert(message: String)
}