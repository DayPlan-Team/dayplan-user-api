package com.user.adapter.tems.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.terms.TermsAgreement
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class TermsAgreementEntity(

    @Column
    val userId: Long,

    @Column
    val termsId: Long,

    @Column
    val isAgreed: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity() {

    fun toTermsAgreement(): TermsAgreement {
        return TermsAgreement(
            userId = userId,
            termsId = termsId,
            isAgreed = isAgreed,
        )
    }
}
