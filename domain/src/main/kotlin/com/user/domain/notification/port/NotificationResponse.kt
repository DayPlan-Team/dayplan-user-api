package com.user.domain.notification.port

data class NotificationResponse<T>(
    val isSuccessful : Boolean,
    val response: T,
)
