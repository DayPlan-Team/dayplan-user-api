package com.user.domain.terms

data class TermsAgreement(
    val userId: Long,
    val termsId: Long,
    val agreement: Boolean,
)
