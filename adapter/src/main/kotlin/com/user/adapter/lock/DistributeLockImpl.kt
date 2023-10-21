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

    override fun withLockRetry(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R
    ): R {
        return lockService.lockRetry(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    override fun withLockUnitRetry(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit
    ) {
        lockService.lockUnitRetry(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    override fun withLockAtomic(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R
    ): R {
        return lockService.lockAtomic(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    override fun withLockUnitAtomic(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit
    ) {
        lockService.lockUnitAtomic(
            key = "${distributeLockType.name}:$key",
            lockTime = lockTime,
            exception = exception,
            action = action,
        )
    }

    companion object : Logger()

}