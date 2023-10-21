package com.user.adapter.terms.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.terms.TermsAgreement
import com.user.domain.terms.TermsAgreementRequest
import com.user.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "terms_agreement",
    indexes = [
        Index(name = "idx_terms_userId", columnList = "userId"),
    ]
)
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

    fun toDomainModel(): TermsAgreement {
        return TermsAgreement(
            userId = userId,
            termsId = termsId,
            agreement = agreement,
        )
    }

    companion object {
        fun from(
            termsAgreementEntity: TermsAgreementEntity,
            termsAgreementRequest: TermsAgreementRequest
        ): TermsAgreementEntity {
            return TermsAgreementEntity(
                id = termsAgreementEntity.id,
                termsId = termsAgreementEntity.termsId,
                userId = termsAgreementEntity.userId,
                agreement = termsAgreementRequest.agreement,
            )
        }

        fun from(user: User, termsAgreementRequest: TermsAgreementRequest): TermsAgreementEntity {
            return TermsAgreementEntity(
                termsId = termsAgreementRequest.termsId,
                userId = user.userId,
                agreement = termsAgreementRequest.agreement,
            )
        }
    }
}
