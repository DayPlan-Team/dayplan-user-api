package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.api.ApiTestConfiguration
import com.user.application.service.UserProfileUpdateService
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = [ApiTestConfiguration::class])
class UserProfileControllerTest : FunSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @MockkBean
    private lateinit var userProfileUpdateService: UserProfileUpdateService

    @Autowired
    private lateinit var mockMvc: MockMvc

    init {

        this.context("유저 프로필 변경에 대한 요청이 주어져요") {
            val userProfileApiRequest = "{\"nickName\": \"shein2\"}"

            every { userProfileUpdateService.upsertUserProfile(any(), any()) } just Runs

            test("유저 프로필 변경에 성공해요") {

                val mockMvcRequestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/user/profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 1L)
                    .content(userProfileApiRequest)

                mockMvc.perform(mockMvcRequestBuilder)
                    .andExpect { it.response.status shouldBe 200 }
            }
        }

        this.context("유저 프로필 변경에 대한 잘못된 json이 주어져요") {
            val userProfileApiRequest = "{\"nick2Name\": \"shein2\"}"

            every { userProfileUpdateService.upsertUserProfile(any(), any()) } just Runs

            test("유저 프로필 변경에 실패해요") {

                val mockMvcRequestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/user/profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("UserId", 1L)
                    .content(userProfileApiRequest)

                mockMvc.perform(mockMvcRequestBuilder)
                    .andExpect { it.response.status shouldBe 400 }
            }
        }
    }

}
