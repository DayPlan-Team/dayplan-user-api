package com.user.application.port.out

import com.user.domain.terms.Terms

interface TermsQueryPort {

    fun findInIds(termsIds: Long): List<Terms>

    fun findById(termsId: Long): Terms

    fun findAll(): List<Terms>
}