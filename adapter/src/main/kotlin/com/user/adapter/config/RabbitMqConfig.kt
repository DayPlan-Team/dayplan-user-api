package com.user.adapter.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

    companion object {
        const val QUEUE_NAME = "notification-queue"
        const val EXCHANGE_NAME = "notification-exchange"
        const val ROUTING_KEY = "notification-key"
    }

    @Bean
    fun queue(): Queue {
        return Queue(QUEUE_NAME, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY)
    }

}