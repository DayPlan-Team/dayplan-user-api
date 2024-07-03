package com.user.adapter.terms.persistence

import com.user.adapter.terms.entity.TermsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermsEntityRepository : JpaRepository<TermsEntity, Long> {
    fun findTermsEntitiesByIdIn(termsId: List<Long>): List<TermsEntity>
}
