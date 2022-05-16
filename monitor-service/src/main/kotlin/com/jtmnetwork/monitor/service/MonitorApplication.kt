package com.jtmnetwork.monitor.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MonitorApplication

fun main(args: Array<String>) {
    runApplication<MonitorApplication>(*args)
}