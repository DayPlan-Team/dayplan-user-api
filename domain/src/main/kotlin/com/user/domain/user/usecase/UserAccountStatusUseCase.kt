package com.user.domain.user.usecase

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import org.springframework.stereotype.Component

@Component
interface UserAccountStatusUseCase {

    fun upsertUserStatus(user: User, userAccountStatus: UserAccountStatus)
}