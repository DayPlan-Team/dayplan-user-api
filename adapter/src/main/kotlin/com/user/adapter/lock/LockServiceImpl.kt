package com.user.adapter.lock

import com.user.util.lock.LockService
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LockServiceImpl<R>(
    private val redissonClient: RedissonClient,
) : LockService<R> {
    override fun lock(key: String, lockTime: Long, exception: Exception, action: () -> R): R {
        val lock = redissonClient.getLock(key)

        try {
            val isLocked = lock.tryLock(lockTime, TimeUnit.MILLISECONDS)

            if (!isLocked) {
                throw exception
            }

            return action()
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    override fun lockUnit(key: String, lockTime: Long, exception: Exception, action: () -> Unit) {
        val lock = redissonClient.getLock(key)

        try {
            val isLocked = lock.tryLock(lockTime, TimeUnit.MILLISECONDS)

            if (!isLocked) {
                throw exception
            }

            action()
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }
}