package com.user.application.port.out

import com.user.domain.terms.TermsAgreement
import com.user.application.request.TermsAgreementRequest
import com.user.domain.user.User
import org.springframework.stereotype.Component

@Component
interface TermsAgreementPort {

    fun findTermsAgreementsByUserId(user: User): List<TermsAgreement>

    fun upsertTermsAgreement(user: User, termsAgreementRequests: List<TermsAgreementRequest>)
}