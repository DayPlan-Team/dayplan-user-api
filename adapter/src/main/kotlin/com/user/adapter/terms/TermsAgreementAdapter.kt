package com.user.adapter.terms

import com.user.adapter.terms.entity.TermsAgreementEntity
import com.user.adapter.terms.persistence.TermsAgreementEntityRepository
import com.user.application.port.out.TermsAgreementPort
import com.user.domain.terms.TermsAgreement
import com.user.application.request.TermsAgreementRequest
import org.springframework.stereotype.Component

@Component
class TermsAgreementAdapter(
    private val termsAgreementEntityRepository: TermsAgreementEntityRepository,
) : TermsAgreementPort {
    override fun findTermsAgreementsByUserId(userId: Long): List<TermsAgreement> {
        return termsAgreementEntityRepository.findAllByUserId(userId).map { it.toTermsAgreement() }
    }

    override fun upsertTermsAgreement(userId: Long, termsAgreementRequests: List<TermsAgreementRequest>) {
        val userTermsAgreementsMap = termsAgreementEntityRepository
            .findAllByUserId(userId)
            .associateBy { it.termsId }

        val termsAgreementEntities = termsAgreementRequests
            .map { termsAgreementRequest ->
                userTermsAgreementsMap[termsAgreementRequest.termsId]?.let {
                    TermsAgreementEntity(
                        id = termsAgreementRequest.termsId,
                        termsId = termsAgreementRequest.termsId,
                        userId = userId,
                        agreement = termsAgreementRequest.agreement,
                    )
                } ?: TermsAgreementEntity(
                    termsId = termsAgreementRequest.termsId,
                    userId = userId,
                    agreement = termsAgreementRequest.agreement,
                )
            }

        termsAgreementEntityRepository.saveAll(termsAgreementEntities)
    }


}