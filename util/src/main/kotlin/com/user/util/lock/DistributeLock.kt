package com.user.util.lock

import org.springframework.stereotype.Component

@Component
interface DistributeLock<R> {
    fun withLock(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R,
    ): R

    fun withLockUnit(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit,
    )
}