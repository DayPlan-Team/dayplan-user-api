package com.user.application.service

import com.user.domain.share.UserAccountStatus
import com.user.domain.terms.Terms
import com.user.domain.terms.TermsAgreementRequest
import com.user.domain.terms.port.TermsAgreementPort
import com.user.domain.terms.port.TermsQueryPort
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import com.user.util.exception.UserException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class TermsAgreementUpsertServiceTest(
    private val userCommandPort: UserCommandPort = mockk(),
    private val termsQueryPort: TermsQueryPort = mockk(),
    private val termsAgreementPort: TermsAgreementPort = mockk(),
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val sut = TermsAgreementUpsertService(
        userCommandPort = userCommandPort,
        termsQueryPort = termsQueryPort,
        termsAgreementPort = termsAgreementPort,
    )

    given("유저와 약관 정보가 주어져요") {
        val user = User(
            email = "gosekose@gosekose",
            userAccountStatus = UserAccountStatus.NORMAL,
            mandatoryTermsAgreed = false,
            nickName = "gosekose",
            userId = 1L,
        )

        every { termsQueryPort.findAll() } returns listOf(
            Terms(
                termsId = 1L,
                sequence = 1,
                content = "서비스 약관",
                mandatory = true,
            ),
            Terms(
                termsId = 2L,
                sequence = 2,
                content = "위치 동의 약관",
                mandatory = true,
            ),
            Terms(
                termsId = 3L,
                sequence = 3,
                content = "선택 약관",
                mandatory = false,
            ),
        )

        `when`("필수 약관을 모두 동의하면") {

            every { termsAgreementPort.upsertTermsAgreement(any(), any()) } just Runs

            every { userCommandPort.save(any()) } returns User(
                email = "gosekose@gosekose",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = true,
                nickName = "gosekose",
                userId = 1L,
            )

            sut.upsertTermsAgreement(
                user = user, listOf(
                    TermsAgreementRequest(
                        termsId = 1L,
                        agreement = true
                    ),
                    TermsAgreementRequest(
                        termsId = 2L,
                        agreement = true
                    ),
                    TermsAgreementRequest(
                        termsId = 3L,
                        agreement = false,
                    ),
                )
            )

            then("예외 발생 없이 모든 약관이 등록되고, 유저의 약관 동의 상태가 변경이 되요") {
                verify(exactly = 1) { termsAgreementPort.upsertTermsAgreement(any(), any()) }
                verify(exactly = 1) { userCommandPort.save(any()) }
            }
        }

        `when`("필수 약관을 하나라도 동의하지 않으면") {

            every { termsAgreementPort.upsertTermsAgreement(any(), any()) } just Runs

            every { userCommandPort.save(any()) } returns User(
                email = "gosekose@gosekose",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = true,
                nickName = "gosekose",
                userId = 1L,
            )

            then("예외 발생가 발생해요") {
                shouldThrow<UserException> {
                    sut.upsertTermsAgreement(
                        user = user, listOf(
                            TermsAgreementRequest(
                                termsId = 1L,
                                agreement = false
                            ),
                            TermsAgreementRequest(
                                termsId = 2L,
                                agreement = true
                            ),
                            TermsAgreementRequest(
                                termsId = 3L,
                                agreement = false,
                            ),
                        )
                    )
                }
            }
        }

        `when`("필수 약관 동의가 하나라도 빠지면") {

            every { termsAgreementPort.upsertTermsAgreement(any(), any()) } just Runs

            every { userCommandPort.save(any()) } returns User(
                email = "gosekose@gosekose",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = true,
                nickName = "gosekose",
                userId = 1L,
            )

            then("예외 발생가 발생해요") {
                shouldThrow<UserException> {
                    sut.upsertTermsAgreement(
                        user = user, listOf(
                            TermsAgreementRequest(
                                termsId = 2L,
                                agreement = true
                            ),
                            TermsAgreementRequest(
                                termsId = 3L,
                                agreement = false,
                            ),
                        )
                    )
                }
            }
        }

        `when`("필수 약관 동의은 모두 동의하되, 선택약관이 빠지면") {

            every { termsAgreementPort.upsertTermsAgreement(any(), any()) } just Runs

            every { userCommandPort.save(any()) } returns User(
                email = "gosekose@gosekose",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = true,
                nickName = "gosekose",
                userId = 1L,
            )

            sut.upsertTermsAgreement(
                user = user, listOf(
                    TermsAgreementRequest(
                        termsId = 1L,
                        agreement = true
                    ),
                    TermsAgreementRequest(
                        termsId = 2L,
                        agreement = true,
                    ),
                )
            )

            then("약관은 정상 등록 되고, 필수 약관은 모두 동의 처리가 되어야 해요") {
                verify(exactly = 1) { termsAgreementPort.upsertTermsAgreement(any(), any()) }
                verify(exactly = 1) { userCommandPort.save(any()) }
            }
        }
    }

})
