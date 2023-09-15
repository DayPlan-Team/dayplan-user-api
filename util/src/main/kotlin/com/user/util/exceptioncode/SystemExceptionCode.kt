package com.user.util.exceptioncode

enum class SystemExceptionCode(
    val status: String,
    val errorCode: String,
    val message: String,
) {
    ENCRYPTION_SECRET_KEY_NOT_INPUT("505", "USR-9000", "암복호화키가 입력되지 않았습니다.")
}