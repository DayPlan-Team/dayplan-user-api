package com.user.application.service

import com.user.domain.user.port.UserQueryPort
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Service

@Service
class UserVerifyService(
    private val userQueryPort: UserQueryPort,
) {
    fun verifyAndGetUser(userId: Long): User {
        val user = userQueryPort.findUserByUserId(userId)

        require(user.userAccountStatus == UserAccountStatus.NORMAL) { throw UserException(UserExceptionCode.USER_STATUS_NOT_NORMAL)}
        require(user.mandatoryTermsAgreed) { throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED)}

        return user
    }
}
