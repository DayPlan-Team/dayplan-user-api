package com.user.domain.terms.request

data class TermsAgreementRequest(
    val termsId: Long,
    val userId: Long,
    val isAgreed: Boolean,
)
