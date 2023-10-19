package com.user.domain.terms

data class TermsAgreementRequest(
    val termsId: Long,
    val agreement: Boolean,
)
