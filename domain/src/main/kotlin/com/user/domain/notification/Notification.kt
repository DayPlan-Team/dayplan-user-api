package com.user.domain.notification

data class Notification<T, R>(
    val sender: T,
    val receiver: T,
    val content: R,
)
