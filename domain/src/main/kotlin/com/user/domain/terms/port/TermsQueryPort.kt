package com.user.domain.terms.port

import com.user.domain.terms.Terms
import org.springframework.stereotype.Component

@Component
interface TermsQueryPort {
    fun findTermsByIdIn(termsIds: List<Long>): List<Terms>

    fun findById(termsId: Long): Terms

    fun findAll(): List<Terms>
}
