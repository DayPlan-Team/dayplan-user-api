package com.user.util.exceptioncode

enum class SystemExceptionCode(
    val status: String,
    val errorCode: String,
    val message: String,
) {
    ENCRYPTION_SECRET_KEY_NOT_INPUT("505", "USR-9000", "암복호화키가 입력되지 않았어요."),
    SOCIAL_LOGIN_TIME_ERROR("505", "USR-9001", "소셜 로그인이 지연되고 있어요. 잠시 후에 요청 부탁드려요"),
}