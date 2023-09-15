package com.user.util.exception

import com.user.util.exceptioncode.UserExceptionCode

class UserException(
    private val code: UserExceptionCode,
) : Error() {
    override val cause: Throwable
        get() = Throwable(code.errorCode)
    override val message: String
        get() = code.message
}