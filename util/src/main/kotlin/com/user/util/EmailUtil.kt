package com.user.util

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode

object EmailUtil {

    fun validateEmail(email: String) {
        require(email.filter { it == '@' }.length == 1) { throw UserException(UserExceptionCode.USER_CREATION_INVALID_EMAIL) }
    }
    
}