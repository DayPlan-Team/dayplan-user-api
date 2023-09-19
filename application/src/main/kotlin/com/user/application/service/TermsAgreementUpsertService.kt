package com.user.application.service

import com.user.application.port.out.TermsAgreementPort
import com.user.application.port.out.TermsQueryPort
import com.user.application.request.TermsAgreementRequest
import com.user.util.exception.SystemException
import com.user.util.exception.UserException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Service

@Service
class TermsAgreementUpsertService(
    private val termsQueryPort: TermsQueryPort,
    private val termsAgreementPort: TermsAgreementPort,
) {

    fun upsertTermsAgreement(userId: Long, termsAgreementRequests: List<TermsAgreementRequest>) {
        val termsMap = termsAgreementRequests.associateBy { it.termsId }
        val terms = termsQueryPort.findAll()

        val termsAgreementRequestForPort = terms.map {
            val termsAgreementRequest =
                termsMap[it.termsId]
                    ?: if (it.mandatory) throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED) else
                        TermsAgreementRequest(it.termsId, false)
            if (it.mandatory) {
                require(termsAgreementRequest.agreement) { throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED) }
            }
            termsAgreementRequest
        }

        termsAgreementPort.upsertTermsAgreement(userId, termsAgreementRequestForPort)
    }
}