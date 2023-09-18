package com.user.application.port.out

import com.user.domain.terms.TermsAgreement
import com.user.application.request.TermsAgreementRequest
import org.springframework.stereotype.Component

@Component
interface TermsAgreementPort {

    fun findTermsAgreementsByUserId(userId: Long): List<TermsAgreement>

    fun upsertTermsAgreement(userId: Long, termsAgreementRequests: List<TermsAgreementRequest>)
}