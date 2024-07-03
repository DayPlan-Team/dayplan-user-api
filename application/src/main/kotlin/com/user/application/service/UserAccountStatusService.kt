package com.user.application.service

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import com.user.domain.user.usecase.UserAccountStatusUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserAccountStatusService(
    private val userCommandPort: UserCommandPort,
) : UserAccountStatusUseCase {
    override fun upsertUserStatus(
        user: User,
        userAccountStatus: UserAccountStatus,
    ) {
        userCommandPort.save(
            user = user.updateUserStatus(userAccountStatus),
        )
    }
}
