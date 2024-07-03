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
    name = "terms_agreements",
    indexes = [
        Index(name = "idx__terms_agreements_user_id", columnList = "user_id"),
    ],
)
data class TermsAgreementEntity(
    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    val userId: Long,
    @Column(name = "terms_id", columnDefinition = "bigint", nullable = false)
    val termsId: Long,
    @Column(name = "is_agreed", columnDefinition = "bit", nullable = false)
    val isAgreed: Boolean,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): TermsAgreement {
        return TermsAgreement(
            userId = userId,
            termsId = termsId,
            agreement = isAgreed,
        )
    }

    companion object {
        fun from(
            termsAgreementEntity: TermsAgreementEntity,
            termsAgreementRequest: TermsAgreementRequest,
        ): TermsAgreementEntity {
            return TermsAgreementEntity(
                id = termsAgreementEntity.id,
                termsId = termsAgreementEntity.termsId,
                userId = termsAgreementEntity.userId,
                isAgreed = termsAgreementRequest.agreement,
            )
        }

        fun from(
            user: User,
            termsAgreementRequest: TermsAgreementRequest,
        ): TermsAgreementEntity {
            return TermsAgreementEntity(
                termsId = termsAgreementRequest.termsId,
                userId = user.userId,
                isAgreed = termsAgreementRequest.agreement,
            )
        }
    }
}
