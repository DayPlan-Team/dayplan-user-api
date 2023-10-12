package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.ApiTestConfiguration
import com.user.application.port.out.TermsQueryPort
import com.user.application.port.out.UserQueryPort
import com.user.application.service.TermsAgreementUpsertService
import com.user.domain.share.UserAccountStatus
import com.user.domain.terms.Terms
import com.user.domain.user.User
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = [ApiTestConfiguration::class])
class TermsControllerTest : FunSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userQueryPort: UserQueryPort

    @MockkBean
    private lateinit var termsQueryPort: TermsQueryPort

    @MockkBean
    private lateinit var termsAgreementUpsertService: TermsAgreementUpsertService

    init {

        this.context("유저에게 제공할 약관들이 주어져요") {
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

                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(TERMS_URL)
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

        this.context("동의해야 할 약관이 주어져요") {
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
            every { termsAgreementUpsertService.upsertTermsAgreement(any(), any()) } just Runs

            test("주어진 약관을 동의하는 요청을 보내면 성공해요") {
                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(TERMS_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 100L)
                    .content("{\"termsAgreements\":[{\"termsId\":10,\"agreement\":true},{\"termsId\":11,\"agreement\":true}]}")

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk)
            }
        }
    }

    companion object {
        const val TERMS_URL = "http://localhost:8080/user/terms"
    }

}
