package com.user.util.exceptioncode

enum class UserExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {


    USER_NOT_VERIFIED(400, "USR0001", "이메일 인증이 되지 않았습니다."),
    USER_STATUS_NOT_NORMAL(400, "USR0002", "이용이 제한된 계정입니다."),
    USER_SOCIAL_INVALID_TYPE(400, "USR0003", "로그인 방식이 잘못되었어요."),
    USER_ACCOUNT_ALREADY_EXISTS(400, "USR0004", "이미 가입한 계정이에요"),
    NOT_FOUND_USER(403, "USR0005", "가입하지 않은 유저에요."),
    BAD_REQUEST_COORDINATES(400, "USR0006", "올바르지 않은 좌표에요"),
    BAD_REQUEST_CITY_CODE(400, "USR0007", "올바르지 않은 시/도에요"),
    BAD_REQUEST_DISTRICT_CODE(400, "USR0008", "올바르지 않은 시/군/구에요"),

    MANDATORY_TERMS_IS_NOT_AGREED(400, "USR1006", "필수 약관은 모두 동의해야해요"),
}