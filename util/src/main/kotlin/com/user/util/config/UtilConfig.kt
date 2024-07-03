package com.user.util.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UtilConfig {
    @Bean
    fun applyObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}
