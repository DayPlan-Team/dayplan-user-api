package com.user.domain.terms.usecase

import com.user.domain.terms.Terms
import com.user.domain.terms.TermsAgreement
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
object TermsAgreementVerifier {

    fun verifyMandatoryTermsIsAgreed(terms: Terms, termsAgreement: TermsAgreement) {
        if (terms.isMandatory) {
            require(termsAgreement.isAgreed) { throw UserException(UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED) }
        }
    }
}