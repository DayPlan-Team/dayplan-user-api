package com.user.util

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode

object PhoneNumberUtil {

    fun validatePhoneNumber(phoneNumber: String) {
        require(phoneNumber.length in 8..20) { throw UserException(UserExceptionCode.USER_CREATION_INVALID_PHONE_NUMBER)}
    }

    fun parsePhoneNumber(phoneNumber: String): String {
        return phoneNumber.filter { it != '-' }
    }

}