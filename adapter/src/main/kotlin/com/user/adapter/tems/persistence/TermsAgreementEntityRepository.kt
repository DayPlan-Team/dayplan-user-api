package com.user.adapter.tems.persistence

import com.user.adapter.tems.entity.TermsAgreementEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TermsAgreementEntityRepository: JpaRepository<TermsAgreementEntity, Long> {
    fun findAllByUserId(userId: Long): List<TermsAgreementEntity>
}