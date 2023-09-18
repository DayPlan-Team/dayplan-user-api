package com.user.domain.terms

import java.time.LocalDateTime

data class TermsAgreement(
    val userId: Long,
    val termsId: Long,
    val isAgreed: Boolean,
)
