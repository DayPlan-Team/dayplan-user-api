package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.TermsAgreementUpsertService
import com.user.domain.terms.Terms
import com.user.domain.terms.TermsAgreementRequest
import com.user.domain.terms.port.TermsQueryPort
import com.user.domain.user.port.UserQueryPort
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/terms")
class TermsController(
    private val userQueryPort: UserQueryPort,
    private val termsQueryPort: TermsQueryPort,
    private val termsAgreementUpsertService: TermsAgreementUpsertService,
) {
    @GetMapping("/check")
    fun checkTermsAgreementToUser(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<UserTermsAgreementStatus> {
        val user = userQueryPort.findUserByUserId(userId)

        return ResponseEntity.ok(
            UserTermsAgreementStatus(
                mandatoryAllAgreement = user.mandatoryTermsAgreed,
            ),
        )
    }

    @GetMapping
    fun getTerms(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<TermsApiResponse> {
        userQueryPort.findUserByUserId(userId)
        val terms = termsQueryPort.findAll().sortedBy { it.sequence }

        return ResponseEntity.ok(
            TermsApiResponse(
                terms,
            ),
        )
    }

    @PostMapping
    fun upsertTermsAgreement(
        @RequestHeader("UserId") userId: Long,
        @RequestBody termsAgreementApiBoxingRequest: TermsAgreementApiBoxingRequest,
    ): ResponseEntity<Unit> {
        val user = userQueryPort.findUserByUserId(userId)

        termsAgreementUpsertService.upsertTermsAgreement(
            user,
            termsAgreementApiBoxingRequest.termsAgreements.map { it.toTermsAgreementRequest() },
        )

        return ResponseEntity.ok().build()
    }

    data class UserTermsAgreementStatus(
        @JsonProperty("mandatoryAllAgreement") val mandatoryAllAgreement: Boolean,
    )

    data class TermsApiResponse(
        val terms: List<Terms>,
    )

    data class TermsAgreementApiBoxingRequest(
        @JsonProperty("termsAgreements") val termsAgreements: List<TermsAgreementApiRequest>,
    )

    data class TermsAgreementApiRequest(
        @JsonProperty("termsId") val termsId: Long,
        @JsonProperty("agreement") val agreement: Boolean,
    ) {
        fun toTermsAgreementRequest(): TermsAgreementRequest {
            return TermsAgreementRequest(
                termsId = termsId,
                agreement = agreement,
            )
        }
    }

    companion object : Logger()
}
