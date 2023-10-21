package com.user.application.service

import com.user.domain.terms.TermsAgreementRequest
import com.user.domain.terms.port.TermsAgreementPort
import com.user.domain.terms.port.TermsQueryPort
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TermsAgreementUpsertService(
    private val userCommandPort: UserCommandPort,
    private val termsQueryPort: TermsQueryPort,
    private val termsAgreementPort: TermsAgreementPort,
) {

    @Transactional
    fun upsertTermsAgreement(user: User, termsAgreementRequests: List<TermsAgreementRequest>) {
        val termsMap = termsAgreementRequests.associateBy { it.termsId }
        val terms = termsQueryPort.findAll()

        val termsAgreements = terms.map {
            val termsAgreementRequest =
                termsMap[it.termsId]
                    ?: if (it.mandatory) throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED) else
                        TermsAgreementRequest(it.termsId, false)
            if (it.mandatory) {
                require(termsAgreementRequest.agreement) { throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED) }
            }
            termsAgreementRequest
        }

        termsAgreementPort.upsertTermsAgreement(user, termsAgreements)
        userCommandPort.save(user.updateMandatoryTermsAgreed(true))
    }
}