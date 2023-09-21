package com.user.application.service

import com.user.application.port.out.UserQueryPort
import com.user.domain.share.UserAccountStatus
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Service

@Service
class UserVerifyService(
    private val userQueryPort: UserQueryPort,
) {
    fun verifyUser(userId: Long) {
        val user = userQueryPort.findUserByUserId(userId)

        require(user.isVerified) { throw UserException(UserExceptionCode.USER_NOT_VERIFIED)}
        require(user.userAccountStatus == UserAccountStatus.NORMAL) { throw UserException(UserExceptionCode.USER_STATUS_NOT_NORMAL)}
        require(user.mandatoryTermsAgreed) { throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED)}
    }
}