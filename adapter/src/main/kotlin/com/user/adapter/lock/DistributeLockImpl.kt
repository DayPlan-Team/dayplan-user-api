package com.user.adapter.lock

import com.user.util.Logger
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import com.user.util.lock.LockService
import org.springframework.stereotype.Component

@Component
class DistributeLockImpl<R>(
    private val lockService: LockService<R>
) : DistributeLock<R> {
    override fun withLock(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R
    ): R {
        return lockService.lock(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    override fun withLockUnit(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit
    ) {
        lockService.lockUnit(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    companion object : Logger()

}