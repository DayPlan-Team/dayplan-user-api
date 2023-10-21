package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.ApiTestConfiguration
import com.user.application.service.UserVerifyService
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.usecase.UserAccountStatusUseCase
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = [ApiTestConfiguration::class])
class UserAccountStatusControllerTest : FunSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userVerifyService: UserVerifyService

    @MockkBean
    private lateinit var userAccountStatusUseCase: UserAccountStatusUseCase

    init {
        this.context("유저 상태 변경에 대한 mock이 주어져요") {

            every { userAccountStatusUseCase.upsertUserStatus(any(), any()) } just Runs

            test("유저가 정상 상태이고, 검증을 통과하면 정상적으로 유저 상태를 변경할 수 있어요.") {

                every { userVerifyService.verifyAndGetUser(any()) } returns User(
                    email = "A@com",
                    userAccountStatus = UserAccountStatus.NORMAL,
                    mandatoryTermsAgreed = false,
                    nickName = "shein",
                    userId = 1L,
                )

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.post("http://localhost:8080/user/status/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", 1L)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
            }

            test("유저가 예외를 발생시키면, 403 에러가 발생해요.") {

                every { userVerifyService.verifyAndGetUser(any()) } throws UserException(UserExceptionCode.USER_STATUS_NOT_NORMAL)

                val mockHttpServletRequestBuilder =
                    MockMvcRequestBuilders.post("http://localhost:8080/user/status/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("UserId", 1L)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isForbidden)
            }
        }
    }

}
