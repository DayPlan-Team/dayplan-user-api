package com.user.application.port.out

import com.user.domain.terms.TermsAgreement
import com.user.domain.terms.request.TermsAgreementRequest

interface TermsAgreementPort {

    fun findTermsAgreementsByUserId(userId: Long): List<TermsAgreement>

    fun upsertTermsAgreement(termsAgreementRequest: TermsAgreementRequest)
}