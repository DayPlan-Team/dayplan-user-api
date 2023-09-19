package com.user.domain.advertising

import java.time.LocalDateTime

data class Advertising<T>(
    val userId: Long,
    val content: T,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
