package com.jtmnetwork.monitor.service.entrypoint.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux
import java.util.*

@Configuration
open class CorsConfiguration {

    /**
     * Setup global CORS configuration.
     */
    @Bean
    open fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("http://localhost:3000")
        corsConfig.maxAge = 8000L
        corsConfig.allowedMethods = listOf("*")
        corsConfig.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }
}