package com.user.api.publics

import com.google.gson.Gson
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.adapter.location.persistence.UserStayedPlaceEntityRepository
import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.api.ApiTestConfiguration
import com.user.application.request.PlaceApiRequest
import com.user.domain.location.PlaceCategory
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = [ApiTestConfiguration::class])
class UserStayedPlaceControllerDistributeTest : FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var placeEntityRepository: PlaceEntityRepository

    @Autowired
    private lateinit var userStayedPlaceEntityRepository: UserStayedPlaceEntityRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var testUserStayedPlaceConfiguration: TestUserStayedPlaceConfiguration

    init {
        context("다수 유저의 동일한 플레이스 요청이 주어져요") {

            val placeApiRequest = PlaceApiRequest(
                placeName = "스타벅스",
                placeCategory = PlaceCategory.CAFE,
                latitude = 127.033234,
                longitude = 37.3232323,
                address = "서울특별시 스프링로 코틀린대로 47",
                roadAddress = "서울특별시 스프링구 코틀린동 37",
                placeUserDescription = "스프링구에 있는 스타벅스",
            )

            test("다수 유저가 머문 플레이스 정보를 저장해요") {

                val threadCount = 5
                val latch = CountDownLatch(threadCount)
                val executorService = Executors.newFixedThreadPool(32)

                for (i in 1 .. threadCount) {
                    executorService.submit {
                        try {
                            val resultABuilder = MockMvcRequestBuilders.post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(USER_ID_HEADER, i)
                                .content(Gson().toJson(placeApiRequest))

                            mockMvc.perform(resultABuilder)
                                .andExpect { it.response.status shouldBe 200 }

                        } finally {
                            latch.countDown()
                        }
                    }
                }
                latch.await()

                val places = placeEntityRepository.findAll().map { it.toPlace() }
                val userStayedPlaces = userStayedPlaceEntityRepository.findAll()
                val userStayedPlaceIds = userStayedPlaces.map { it.placeId }.toSet()

                places.size shouldBe 1L
                places[0].address shouldBe  "서울특별시 스프링로 코틀린대로 47"
                places[0].userRegistrationCount shouldBe 5L

                userStayedPlaces.size shouldBe 5L
                userStayedPlaceIds.size shouldBe 1L
            }
        }
    }

    companion object {
        const val BASE_URL = "http://localhost:8080/user/place"
        const val USER_ID_HEADER = "UserId"
    }

    @TestConfiguration
    class TestUserStayedPlaceConfiguration(
        @Autowired private val userEntityRepository: UserEntityRepository,
    ) {
        @PostConstruct
        fun addUser() {
            for (id in 1..10) {
                userEntityRepository.save(
                    UserEntity.from(
                        User(
                            email = "shein${id}@naver.com",
                            userAccountStatus = UserAccountStatus.NORMAL,
                            isVerified = true,
                            mandatoryTermsAgreed = true,
                            nickName = "GoseKose",
                            userId = id.toLong(),
                        )
                    )
                )
            }
        }
    }
}
