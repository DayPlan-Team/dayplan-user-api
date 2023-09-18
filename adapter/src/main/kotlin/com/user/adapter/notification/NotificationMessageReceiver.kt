package com.user.adapter.notification

import org.springframework.amqp.rabbit.annotation.RabbitListener

class NotificationMessageReceiver {

    @RabbitListener(queues = [])
    fun receiveMessage(message: String) {
        TODO()
    }

}