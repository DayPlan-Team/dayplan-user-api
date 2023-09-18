package com.user.adapter.tems.persistence

import com.user.adapter.tems.entity.TermsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TermsEntityRepository : JpaRepository<TermsEntity, Long> {

    fun findTermsEntitiesByIdIn(termsId: List<Long>): List<TermsEntity>
}