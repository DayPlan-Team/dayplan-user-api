package com.user.domain.terms

data class Terms(
    val termsId: Long,
    val sequence: Int,
    val content: String,
    val mandatory: Boolean,
)
