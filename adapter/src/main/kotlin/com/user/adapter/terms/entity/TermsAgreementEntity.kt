package com.user.adapter.terms.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.terms.TermsAgreement
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "terms_agreement")
data class TermsAgreementEntity(

    @Column
    val userId: Long,

    @Column
    val termsId: Long,

    @Column
    val agreement: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity() {

    fun toTermsAgreement(): TermsAgreement {
        return TermsAgreement(
            userId = userId,
            termsId = termsId,
            agreement = agreement,
        )
    }
}
