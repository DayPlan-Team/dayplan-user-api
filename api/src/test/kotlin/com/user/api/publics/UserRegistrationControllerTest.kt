package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.exception.ExceptionController
import com.user.application.service.UserRegistrationService
import com.user.domain.authentication.AuthenticationTicket
import com.user.domain.authentication.usecase.AuthenticationTicketUseCase
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.util.social.SocialType
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ActiveProfiles("test")
class UserRegistrationControllerTest : FunSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @MockkBean
    private lateinit var userRegistrationService: UserRegistrationService

    @MockkBean
    private lateinit var authenticationTicketUseCase: AuthenticationTicketUseCase

    private lateinit var mockMvc: MockMvc

    override suspend fun beforeSpec(spec: Spec) {
        val userRegistrationController = UserRegistrationController(
            userRegistrationService = userRegistrationService,
            authenticationTicketUseCase = authenticationTicketUseCase,
        )

        val exceptionController = ExceptionController()

        mockMvc = MockMvcBuilders
            .standaloneSetup(userRegistrationController)
            .setControllerAdvice(exceptionController)
            .build()
    }

    init {
        context("유저를 등록하기 위한 요청 정보가 주어져요") {

            val user = User(
                email = "shein@com",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = false,
                nickName = "shein",
                userId = 100L,
            )

            val authenticationTicket = AuthenticationTicket(
                accessToken = "12345678901234567890",
                refreshToken = "12345678901234567890",
            )

            every { userRegistrationService.createUserIfSocialRegistrationNotExists(any()) } returns user
            every { authenticationTicketUseCase.createAuthenticationTicket(any()) } returns authenticationTicket

            test("유저가 잘못된 소셜 타입으로 유저를 생성하면, 실패해요") {

                val code = "1234567"
                val registrationId = "1234567"

                val mockMvcRequestBuilder =
                    MockMvcRequestBuilders.get(
                        "http://localhost:8080/user/registration/social/{registrationId}",
                        registrationId
                    )
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("code", code)

                mockMvc.perform(mockMvcRequestBuilder)
                    .andExpect { it.response.status shouldBe 400 }
            }

            test("유저가 구글로 등록하면 성공해요") {

                val code = "1234567"
                val registrationId = SocialType.GOOGLE.registrationId

                val mockMvcRequestBuilder =
                    MockMvcRequestBuilders.get(
                        "http://localhost:8080/user/registration/social/{registrationId}",
                        registrationId
                    )
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("code", code)

                mockMvc.perform(mockMvcRequestBuilder)
                    .andExpect { it.response.status shouldBe 200 }
            }
        }
    }
}
