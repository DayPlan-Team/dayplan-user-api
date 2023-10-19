package com.user.adapter.terms

import com.user.adapter.terms.entity.TermsAgreementEntity
import com.user.adapter.terms.persistence.TermsAgreementEntityRepository
import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.domain.terms.port.TermsAgreementPort
import com.user.domain.terms.TermsAgreement
import com.user.domain.terms.TermsAgreementRequest
import com.user.domain.user.User
import org.springframework.stereotype.Component

@Component
class TermsAgreementAdapter(
    private val termsAgreementEntityRepository: TermsAgreementEntityRepository,
    private val userEntityRepository: UserEntityRepository,
) : TermsAgreementPort {
    override fun findTermsAgreementsByUserId(user: User): List<TermsAgreement> {
        return termsAgreementEntityRepository.findAllByUserId(user.userId).map { it.toTermsAgreement() }
    }

    override fun upsertTermsAgreement(user: User, termsAgreementRequests: List<TermsAgreementRequest>) {
        val userTermsAgreementsMap = termsAgreementEntityRepository
            .findAllByUserId(user.userId)
            .associateBy { it.termsId }

        val termsAgreementEntities = termsAgreementRequests
            .map { termsAgreementRequest ->
                userTermsAgreementsMap[termsAgreementRequest.termsId]?.let {
                    TermsAgreementEntity(
                        id = termsAgreementRequest.termsId,
                        termsId = termsAgreementRequest.termsId,
                        userId = user.userId,
                        agreement = termsAgreementRequest.agreement,
                    )
                } ?: TermsAgreementEntity(
                    termsId = termsAgreementRequest.termsId,
                    userId = user.userId,
                    agreement = termsAgreementRequest.agreement,
                )
            }

        termsAgreementEntityRepository.saveAll(termsAgreementEntities)
        userEntityRepository.save(UserEntity.from(user.from(true)))
    }
}