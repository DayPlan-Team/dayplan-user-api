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
        val termsEntity = termsEntityRepository.findTermsEntitiesByIdIn(termsIds)

        return termsEntity.map {
            it.toDomainModel()
        }
    }

    override fun findById(termsId: Long): Terms {
        val termsEntity = termsEntityRepository
            .findById(termsId)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_TERMS) }

        return termsEntity.toDomainModel()
    }

    override fun findAll(): List<Terms> {
        return termsEntityRepository.findAll()
            .map { it.toDomainModel() }
    }
}