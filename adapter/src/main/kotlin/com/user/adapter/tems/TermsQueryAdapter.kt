package com.user.adapter.tems

import com.user.adapter.tems.entity.TermsEntity
import com.user.adapter.tems.persistence.TermsEntityRepository
import com.user.application.port.out.TermsQueryPort
import com.user.domain.terms.Terms
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode

class TermsQueryAdapter(
    private val termsEntityRepository: TermsEntityRepository,
) : TermsQueryPort {
    override fun findTermsByIdIn(termsIds: List<Long>): List<Terms> {
        val termsEntity = termsEntityRepository.findTermsEntitiesByIdIn(termsIds)
        return termsEntity.map {
            it.toTerms()
        }
    }

    override fun findById(termsId: Long): Terms {
        val termsEntity = termsEntityRepository
            .findById(termsId)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_TERMS) }

        return termsEntity.toTerms()
    }

    override fun findAll(): List<Terms> {
        return termsEntityRepository.findAll()
            .map { it.toTerms() }
    }
}