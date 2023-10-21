package com.user.adapter.terms

import com.user.adapter.terms.persistence.TermsEntityRepository
import com.user.domain.terms.port.TermsQueryPort
import com.user.domain.terms.Terms
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import org.springframework.stereotype.Component

@Component
class TermsQueryAdapter(
    private val termsEntityRepository: TermsEntityRepository,
) : TermsQueryPort {

    override fun findTermsByIdIn(termsIds: List<Long>): List<Terms> {
        return termsEntityRepository
            .findTermsEntitiesByIdIn(termsIds)
            .map { it.toDomainModel() }
    }

    override fun findById(termsId: Long): Terms {
        return termsEntityRepository
            .findById(termsId)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_TERMS) }
            .toDomainModel()
    }

    override fun findAll(): List<Terms> {
        return termsEntityRepository
            .findAll()
            .map { it.toDomainModel() }
    }
}