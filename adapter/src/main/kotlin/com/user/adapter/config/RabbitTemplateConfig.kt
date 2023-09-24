package com.user.adapter.config

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class RabbitTemplateConfig {

    @Bean
    fun applyRabbitTemplate(connectionFactory: CachingConnectionFactory): RabbitTemplate {
        return  RabbitTemplate(connectionFactory)
    }

}