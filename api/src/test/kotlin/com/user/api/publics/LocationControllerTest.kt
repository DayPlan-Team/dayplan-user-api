package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.ApiTestConfiguration
import com.user.application.service.UserLocationService
import com.user.application.service.UserVerifyService
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = [ApiTestConfiguration::class])
class LocationControllerTest : FunSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userVerifyService: UserVerifyService

    @MockkBean
    private lateinit var userLocationService: UserLocationService

    init {

        this.context("유저 정보 및 유저 검증 및 저장에 대한 mock이 주어져요") {
            val user = User(
                email = "shein1@naver.com",
                userAccountStatus = UserAccountStatus.NORMAL,
                mandatoryTermsAgreed = true,
                nickName = "GoseKose",
                userId = 1,
            )

            every { userVerifyService.verifyAndGetUser(any()) } returns user

            every { userLocationService.upsertUserLocation(any(), any()) } just Runs

            test("유효한 범위에 대한 위치 정보가 주어지면 요청을 정상 처리해요") {
                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/user/location")
                    .header("UserId", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"latitude\": 39.10, \"longitude\": 129.30}"
                    )

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk)
            }

            test("잘못된 범위에 대한 위치 정보가 주어지면 요청은 400 처리해요") {
                val mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/user/location")
                    .header("UserId", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"latitude\": 40.00, \"longitude\": 129.30}"
                    )

                mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            }
        }
    }

}