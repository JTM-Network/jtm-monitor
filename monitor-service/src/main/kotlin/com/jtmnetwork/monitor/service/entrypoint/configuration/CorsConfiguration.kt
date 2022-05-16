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

    @Bean
    open fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = Arrays.asList("http://localhost:3000")
        corsConfig.maxAge = 8000L
        corsConfig.allowedMethods = Arrays.asList("OPTION", "POST", "PUT", "GET", "DELETE", "PATCH")
        corsConfig.allowedHeaders = Arrays.asList("")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }
}