package com.user.domain.terms

data class Terms(
    val id: Long,
    val sequence: Long,
    val content: String,
    val isMandatory: Boolean,
)
