package com.user.domain.terms

data class Terms(
    val termsId: Long,
    val sequence: Long,
    val content: String,
    val mandatory: Boolean,
)
