package com.user.api.publics

import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.api.ApiTestConfiguration
import com.user.domain.location.PlaceCategory
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
class PlaceSearchControllerDistributeLockTest : FunSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var placeEntityRepository: PlaceEntityRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var placeSearchControllerTestConfiguration: PlaceSearchControllerTestConfiguration

    init {
        context("유저가 place를 검색할 기본 조건이 주어져요") {

            val citycode = CityCode.SEOUL.code
            val districtcode = DistrictCode.SEOUL_DOBONG.code
            val place = PlaceCategory.CAFE


            test("한명의 place 검색 요청이 주어지면") {
                val resultBuilder = MockMvcRequestBuilders.get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(USER_ID_HEADER, 1)
                    .param("citycode", citycode.toString())
                    .param("districtcode", districtcode.toString())
                    .param("place", place.toString())

                runBlocking {
                    mockMvc.perform(resultBuilder)
                        .andExpect { it.response.status shouldBe 200 }
                    println("size = ${placeEntityRepository.findAll()}")
                }
            }

            test("다수의 place 검색 요청이 주어지면") {
                val threadCount = 5
                val latch = CountDownLatch(threadCount)
                val executorService = Executors.newFixedThreadPool(32)

                runBlocking {
                    launch {
                        for (i in 1..threadCount) {
                            executorService.submit {
                                try {
                                    val resultBuilder = MockMvcRequestBuilders.get(BASE_URL)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .header(USER_ID_HEADER, i)
                                        .param("citycode", citycode.toString())
                                        .param("districtcode", districtcode.toString())
                                        .param("place", place.toString())

                                    mockMvc.perform(resultBuilder)
                                        .andExpect { it.response.status shouldBe 200 }

                                } finally {
                                    latch.countDown()
                                }
                            }
                        }
                    }
                }
                latch.await()
                val result = placeEntityRepository.findAll()
                result.size shouldBe 1L
            }
        }
    }

    companion object {
        private const val BASE_URL = "http://localhost:8080/user/place/search"
        private const val USER_ID_HEADER = "UserId"
    }

    @TestConfiguration
    class PlaceSearchControllerTestConfiguration(
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
