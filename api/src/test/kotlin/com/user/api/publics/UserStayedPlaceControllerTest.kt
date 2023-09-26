package com.user.api.publics

import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import com.user.api.ApiTestConfiguration
import com.user.application.request.PlaceApiRequest
import com.user.application.service.UserVerifyService
import com.user.domain.location.PlaceCategory
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
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
class UserStayedPlaceControllerTest : FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @MockkBean
    private lateinit var userVerifyService: UserVerifyService

    @Autowired
    private lateinit var mockMvc: MockMvc

    init {
        context("유저의 플레이스 저장을 위해 요청을 처리해요") {
            val userId = 1L

            val user = User(
                email = "shein@naver.com",
                userAccountStatus = UserAccountStatus.NORMAL,
                isVerified = true,
                mandatoryTermsAgreed = true,
                nickName = "GoseKose",
                userId = userId,
            )

            val placeApiRequest = PlaceApiRequest(
                placeName = "스타벅스",
                placeCategory = PlaceCategory.CAFE,
                latitude = 127.033234,
                longitude = 37.3232323,
                address = "서울특별시 스프링로 코틀린대로 47",
                roadAddress = "서울특별시 스프링구 코틀린동 37",
                placeUserDescription = "스프링구에 있는 스타벅스",
            )

            every { userVerifyService.verifyAndGetUser(userId) } returns user

            test("유저가 머문 플레이스 정보를 저장해요") {
                val resultBuilder = MockMvcRequestBuilders.post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(USER_ID_HEADER, USER_ID_HEADER_VALUE)
                    .content(Gson().toJson(placeApiRequest))

                mockMvc.perform(resultBuilder)
                    .andReturn()

            }
        }
    }

    companion object {
        const val BASE_URL = "http://localhost:8080/user/place"
        const val USER_ID_HEADER = "UserId"
        const val USER_ID_HEADER_VALUE = 1L
    }
}
