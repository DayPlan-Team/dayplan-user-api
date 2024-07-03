package com.user.util.exception

import com.user.util.exceptioncode.SystemExceptionCode

class SystemException(
    val code: SystemExceptionCode,
) : RuntimeException() {
    override val cause: Throwable
        get() = Throwable(code.errorCode)
    override val message: String
        get() = code.message

    val status: Int
        get() = code.status

    val errorCode: String
        get() = code.errorCode
}
