package com.user.domain.terms

import java.time.LocalDateTime

data class TermsAgreement(
    val userId: Long,
    val terms: Terms,
    val isAgreed: Boolean,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
