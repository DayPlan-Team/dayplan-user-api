package com.user.util.exceptioncode

enum class UserExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {


    USER_CREATION_INVALID_PASSWORD(400, "USR0001", "패스워드가 일치하지 않아요."),
    USER_CREATION_INVALID_PHONE_NUMBER(400, "USR0002", "잘못된 휴대폰 번호에요."),
    USER_CREATION_INVALID_EMAIL(400, "USR0003", "잘못된 이메일이에요."),
    USER_SOCIAL_INVALID_TYPE(400, "USR0004", "로그인 방식이 잘못되었어요."),
    USER_ACCOUNT_ALREADY_EXISTS(400, "USR0004", "이미 가입한 계정이에요"),
    NOT_FOUND_USER(400, "USR0005", "가입하지 않은 유저에요."),

    MANDATORY_TERMS_IS_NOT_AGREED(400, "USR1006", "필수 약관은 모두 동의해야해요"),
}