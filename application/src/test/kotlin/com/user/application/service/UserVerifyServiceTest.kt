package com.user.application.service

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.port.UserQueryPort
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserVerifyServiceTest(
    private val userQueryPort: UserQueryPort = mockk(),
) : FunSpec({

        val sut =
            UserVerifyService(
                userQueryPort = userQueryPort,
            )

        context("유저가 정상 유저이고 모든 약관을 동의했으면") {
            val user =
                User(
                    email = "shein@com",
                    userAccountStatus = UserAccountStatus.NORMAL,
                    mandatoryTermsAgreed = true,
                    nickName = "shein",
                    userId = 10L,
                )

            every { userQueryPort.findUserByUserId(any()) } returns user

            test("검증을 정상 통과해요") {
                shouldNotThrow<UserException> {
                    sut.verifyAndGetUser(1L)
                }
            }
        }

        context("유저가 정상 유저이지만, 모든 약관을 동의하지 않았으면") {
            val user =
                User(
                    email = "shein@com",
                    userAccountStatus = UserAccountStatus.NORMAL,
                    mandatoryTermsAgreed = false,
                    nickName = "shein",
                    userId = 10L,
                )

            every { userQueryPort.findUserByUserId(any()) } returns user

            test("검증 예외가 발생해요") {
                shouldThrow<UserException> {
                    sut.verifyAndGetUser(1L)
                }.message shouldBe UserExceptionCode.MANDATORY_TERMS_IS_NOT_AGREED.message
            }
        }

        context("유저가 탈퇴한 유저라면") {
            val user =
                User(
                    email = "shein@com",
                    userAccountStatus = UserAccountStatus.WITHDRAWAL,
                    mandatoryTermsAgreed = true,
                    nickName = "shein",
                    userId = 10L,
                )

            every { userQueryPort.findUserByUserId(any()) } returns user

            test("검증을 정상 통과해요") {
                shouldThrow<UserException> {
                    sut.verifyAndGetUser(1L)
                }.message shouldBe UserExceptionCode.USER_STATUS_NOT_NORMAL.message
            }
        }
    })
