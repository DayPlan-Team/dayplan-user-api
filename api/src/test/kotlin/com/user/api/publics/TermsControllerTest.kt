package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.exception.ExceptionController
import com.user.application.service.TermsAgreementUpsertService
import com.user.domain.share.UserAccountStatus
import com.user.domain.terms.Terms
import com.user.domain.terms.port.TermsQueryPort
import com.user.domain.user.User
import com.user.domain.user.port.UserQueryPort
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.hamcrest.Matchers
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ActiveProfiles("test")
class TermsControllerTest : FunSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @MockkBean
    private lateinit var userQueryPort: UserQueryPort

    @MockkBean
    private lateinit var termsQueryPort: TermsQueryPort

    @MockkBean
    private lateinit var termsAgreementUpsertService: TermsAgreementUpsertService

    private lateinit var mockMvc: MockMvc

    override suspend fun beforeSpec(spec: Spec) {
        val termsController = TermsController(
            userQueryPort = userQueryPort,
            termsQueryPort = termsQueryPort,
            termsAgreementUpsertService = termsAgreementUpsertService,
        )

        val exceptionController = ExceptionController()

        mockMvc = MockMvcBuilders
            .standaloneSetup(termsController)
            .setControllerAdvice(exceptionController)
            .build()
    }

    init {
        context("유저의 약관 동의 이력이 주어져요") {

            val user = User(
                email = "shein@com",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = false,
                nickName = "shein",
                userId = 100L,
            )

            every { userQueryPort.findUserByUserId(any()) } returns user

            test("유저의 필수 약관 동의 여부를 제공해요") {

                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/user/terms/check")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 100L)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.mandatoryAllAgreement").value(false))
            }
        }


        context("유저에게 제공할 약관들이 주어져요") {
            val terms = listOf(
                Terms(
                    termsId = 10L,
                    sequence = 2L,
                    content = "약관2",
                    mandatory = false,
                ),
                Terms(
                    termsId = 11L,
                    sequence = 1L,
                    content = "약관1",
                    mandatory = true,
                ),
            )

            val user = User(
                email = "shein@com",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = false,
                nickName = "shein",
                userId = 100L,
            )

            every { userQueryPort.findUserByUserId(any()) } returns user
            every { termsQueryPort.findAll() } returns terms

            test("약관을 요청하면 순서대로 정렬해서 약관을 제공해요") {

                val expectedItem1 = mapOf(
                    "termsId" to 11,
                    "sequence" to 1,
                    "content" to "약관1",
                    "mandatory" to true
                )

                val expectedItem2 = mapOf(
                    "termsId" to 10,
                    "sequence" to 2,
                    "content" to "약관2",
                    "mandatory" to false
                )

                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/user/terms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 100L)

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.terms").isArray())
                    .andExpect(
                        jsonPath("$.terms").value(
                            Matchers.containsInAnyOrder(
                                expectedItem1, expectedItem2
                            )
                        )
                    )
            }
        }

        context("동의해야 할 약관이 주어져요") {
            val user = User(
                email = "shein@com",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = false,
                nickName = "shein",
                userId = 100L,
            )

            every { userQueryPort.findUserByUserId(any()) } returns user
            every { termsAgreementUpsertService.upsertTermsAgreement(any(), any()) } just Runs

            test("주어진 약관을 동의하는 요청을 보내면 성공해요") {
                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/user/terms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 100L)
                    .content("{\"termsAgreements\":[{\"termsId\":10,\"agreement\":true},{\"termsId\":11,\"agreement\":true}]}")

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
            }
        }
    }
}
