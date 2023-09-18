package com.user.adapter.tems

import com.user.application.port.out.TermsAgreementPort
import com.user.domain.terms.TermsAgreement
import com.user.domain.terms.request.TermsAgreementRequest

class TermsAgreementAdapter : TermsAgreementPort {
    override fun findTermsAgreementsByUserId(userId: Long): List<TermsAgreement> {
        TODO("Not yet implemented")
    }

    override fun upsertTermsAgreement(termsAgreementRequest: TermsAgreementRequest) {
        TODO("Not yet implemented")
    }
}