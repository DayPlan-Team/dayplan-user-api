package com.user.util.lock

import org.springframework.stereotype.Component

@Component
interface LockService<R> {
    fun lock(key: String, lockTime: Long, exception: Exception, action: () -> R): R

    fun lockUnit(key: String, lockTime: Long, exception: Exception, action: () -> Unit)
}