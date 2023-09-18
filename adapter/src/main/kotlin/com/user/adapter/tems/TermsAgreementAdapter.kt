package com.user.adapter.tems

import com.user.adapter.tems.entity.TermsAgreementEntity
import com.user.adapter.tems.persistence.TermsAgreementEntityRepository
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
                        isAgreed = termsAgreementRequest.isAgreed,
                    )
                } ?: TermsAgreementEntity(
                    termsId = termsAgreementRequest.termsId,
                    userId = userId,
                    isAgreed = termsAgreementRequest.isAgreed,
                )
            }

        termsAgreementEntityRepository.saveAll(termsAgreementEntities)
    }


}