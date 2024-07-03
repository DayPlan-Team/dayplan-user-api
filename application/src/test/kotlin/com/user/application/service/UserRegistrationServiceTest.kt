package com.user.application.service

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import com.user.domain.user.port.UserQueryPort
import com.user.domain.user.request.UserAccountSocialCreationRequest
import com.user.util.social.SocialType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserRegistrationServiceTest(
    private val userCommandPort: UserCommandPort = mockk(),
    private val userAccountSocialSourcePort: UserAccountSocialSourcePort = mockk(),
    private val userQueryPort: UserQueryPort = mockk(),
) : BehaviorSpec({

        isolationMode = IsolationMode.InstancePerTest

        val sut =
            UserRegistrationService(
                userCommandPort = userCommandPort,
                userAccountSocialSourcePort = userAccountSocialSourcePort,
                userQueryPort = userQueryPort,
            )

        given("유저 저장에 대한 stub을 정의해요") {

            val user =
                User(
                    email = "shein@com",
                    userAccountStatus = UserAccountStatus.NORMAL,
                    mandatoryTermsAgreed = false,
                    nickName = "shein",
                    userId = 10L,
                )

            val userAccountSocialCreationRequest =
                UserAccountSocialCreationRequest(
                    code = "1234",
                    socialType = SocialType.GOOGLE,
                )

            every { userCommandPort.save(any()) } returns user

            `when`("소셜 유저 정보를 정상 반환하고, 유저가 처음 가입했을 때") {

                every {
                    userAccountSocialSourcePort.getSocialUserSource(
                        any(),
                        any(),
                    )
                } returns UserSourceResponse(email = "shein@naver.com")
                every { userQueryPort.findUserByEmailOrNull(any()) } returns null

                sut.createUserIfSocialRegistrationNotExists(userAccountSocialCreationRequest)

                then("유저 저장을 수행해야 해요") {
                    verify(exactly = 1) { userCommandPort.save(any()) }
                }
            }

            `when`("소셜 유저 정보를 정상 반환하고, 유저가 이미 가입했을 때") {

                every {
                    userAccountSocialSourcePort.getSocialUserSource(
                        any(),
                        any(),
                    )
                } returns UserSourceResponse(email = "shein@naver.com")
                every { userQueryPort.findUserByEmailOrNull(any()) } returns user

                sut.createUserIfSocialRegistrationNotExists(userAccountSocialCreationRequest)

                then("유저 저장은 수행하지 않아야 해요") {
                    verify(exactly = 0) { userCommandPort.save(any()) }
                }
            }

            `when`("소셜 유저 정보에서 에러가 발생해요") {

                every { userAccountSocialSourcePort.getSocialUserSource(any(), any()) } throws IllegalArgumentException()
                every { userQueryPort.findUserByEmailOrNull(any()) } returns user

                then("다음 절차인 유저 쿼리와 유저 저장은 수행하지 않아야 해요") {

                    shouldThrow<IllegalArgumentException> {
                        sut.createUserIfSocialRegistrationNotExists(userAccountSocialCreationRequest)
                    }
                    verify(exactly = 0) { userQueryPort.findUserByEmailOrNull(any()) }
                    verify(exactly = 0) { userCommandPort.save(any()) }
                }
            }
        }
    })
