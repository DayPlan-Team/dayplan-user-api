package com.user.application.request

data class TermsAgreementRequest(
    val termsId: Long,
    val agreement: Boolean,
)
