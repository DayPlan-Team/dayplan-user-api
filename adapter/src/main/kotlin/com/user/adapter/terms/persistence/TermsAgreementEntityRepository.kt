package com.user.adapter.terms.persistence

import com.user.adapter.terms.entity.TermsAgreementEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TermsAgreementEntityRepository: JpaRepository<TermsAgreementEntity, Long> {
    fun findAllByUserId(userId: Long): List<TermsAgreementEntity>
}