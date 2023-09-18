package com.user.adapter.tems

import com.user.application.port.out.TermsQueryPort
import com.user.domain.terms.Terms

class TermsQueryAdapter : TermsQueryPort {
    override fun findInIds(termsIds: Long): List<Terms> {
        TODO("Not yet implemented")
    }

    override fun findById(termsId: Long): Terms {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Terms> {
        TODO("Not yet implemented")
    }

}