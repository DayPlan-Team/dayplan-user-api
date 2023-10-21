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
        return termsAgreementEntityRepository.findAllByUserId(user.userId).map { it.toDomainModel() }
    }

    override fun upsertTermsAgreement(user: User, termsAgreementRequests: List<TermsAgreementRequest>) {
        val termsAgreementEntities = toTermsAgreementEntities(
            user = user,
            request = termsAgreementRequests,
        )

        termsAgreementEntityRepository.saveAll(termsAgreementEntities)
        userEntityRepository.save(UserEntity.from(user.from(true)))
    }

    private fun toTermsAgreementEntities(user: User, request: List<TermsAgreementRequest>): List<TermsAgreementEntity> {
        val userTermsAgreementsMap = termsAgreementEntityRepository
            .findAllByUserId(user.userId)
            .associateBy { it.termsId }

        return request
            .map { termsAgreementRequest ->
                userTermsAgreementsMap[termsAgreementRequest.termsId]?.let { agreement ->
                    TermsAgreementEntity.from(agreement, termsAgreementRequest)
                } ?: TermsAgreementEntity.from(user, termsAgreementRequest)
            }
    }
}