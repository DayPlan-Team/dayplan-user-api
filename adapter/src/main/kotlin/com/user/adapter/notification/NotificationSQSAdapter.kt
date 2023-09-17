package com.user.adapter.notification

import com.user.domain.notification.Notification
import com.user.domain.notification.port.NotificationPort
import com.user.domain.notification.port.NotificationResponse

class NotificationSQSAdapter<E, T, R> : NotificationPort<E, T, R> {
    override fun sendNotification(notification: Notification<E, T>): NotificationResponse<R> {
        TODO("Not yet implemented")
    }

    override fun receiveNotification(notification: Notification<E, T>): NotificationResponse<R> {
        TODO("Not yet implemented")
    }

}