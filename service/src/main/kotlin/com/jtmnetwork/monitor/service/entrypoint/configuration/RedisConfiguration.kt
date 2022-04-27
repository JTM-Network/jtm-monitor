package com.jtmnetwork.monitor.service.entrypoint.configuration

import com.jtmnetwork.monitor.service.core.domain.entity.CachedServer
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
open class RedisConfiguration {

    @Bean
    open fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, CachedServer> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(CachedServer::class.java)
        val builder: RedisSerializationContext.RedisSerializationContextBuilder<String, CachedServer> = RedisSerializationContext.newSerializationContext(keySerializer)
        val context = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(factory, context)
    }
}