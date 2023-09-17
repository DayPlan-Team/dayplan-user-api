package com.user.domain.notification.port

import com.user.domain.notification.Notification
import org.springframework.stereotype.Component

@Component
interface NotificationPort<E, T, R> {

    fun sendNotification(notification: Notification<E, T>): NotificationResponse<R>

    fun receiveNotification(notification: Notification<E, T>): NotificationResponse<R>
}