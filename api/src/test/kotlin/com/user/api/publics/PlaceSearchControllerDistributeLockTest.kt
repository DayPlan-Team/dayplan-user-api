package com.user.api.publics

import com.ninjasquad.springmockk.MockkBean
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.api.ApiTestConfiguration
import com.user.application.port.out.PlaceSearchPort
import com.user.application.response.PlaceItem
import com.user.application.response.PlaceItemResponse
import com.user.application.service.UserVerifyService
import com.user.domain.location.PlaceCategory
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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

    @MockkBean
    private lateinit var userVerifyService: UserVerifyService

    @MockkBean
    private lateinit var placeSearchPort: PlaceSearchPort

    @Autowired
    private lateinit var placeEntityRepository: PlaceEntityRepository

    @Autowired
    private lateinit var mockMvc: MockMvc


    init {

        context("유저가 place를 검색할 기본 조건이 주어져요") {

            every { userVerifyService.verifyAndGetUser(1) } returns User(
                email = "shein1@naver.com",
                userAccountStatus = UserAccountStatus.NORMAL,
                isVerified = true,
                mandatoryTermsAgreed = true,
                nickName = "GoseKose",
                userId = 1,
            )

            every { userVerifyService.verifyAndGetUser(2) } returns User(
                email = "shein2@naver.com",
                userAccountStatus = UserAccountStatus.NORMAL,
                isVerified = true,
                mandatoryTermsAgreed = true,
                nickName = "GoseKose",
                userId = 2,
            )

            every { userVerifyService.verifyAndGetUser(3) } returns User(
                email = "shein3@naver.com",
                userAccountStatus = UserAccountStatus.NORMAL,
                isVerified = true,
                mandatoryTermsAgreed = true,
                nickName = "GoseKose",
                userId = 3,
            )

            every { placeSearchPort.searchLocation(any(), any()) } returns PlaceItemResponse(
                lastBuildDate = "",
                total = 3,
                start = 1,
                display = 3,
                items = listOf(
                    PlaceItem(
                        title = "당근",
                        link = "www.karrot.com",
                        category = "카페",
                        description = "중고마켓",
                        telephone = "010-0000-1111",
                        address = "강남역 교보타워1",
                        roadAddress = "강남역 교보타워 1",
                        mapx = "123001002",
                        mapy = "3701002",
                    ),
                    PlaceItem(
                        title = "당근페이",
                        link = "www.karrotpay.com",
                        category = "카페",
                        description = "페이",
                        telephone = "010-0000-1112",
                        address = "강남역 교보타워2",
                        roadAddress = "강남역 교보타워 2",
                        mapx = "123001003",
                        mapy = "3701003",
                    ),
                ),
            )

            val citycode = CityCode.SEOUL.code
            val districtcode = DistrictCode.SEOUL_DOBONG.code
            val place = PlaceCategory.CAFE

            @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
            test("한명의 place 검색 요청이 주어지면") {
                runTest {
                    val resultBuilder = MockMvcRequestBuilders.get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(USER_ID_HEADER, 1)
                        .param("citycode", citycode.toString())
                        .param("districtcode", districtcode.toString())
                        .param("place", place.toString())

                    mockMvc.perform(resultBuilder)
                        .andExpect { it.response.status shouldBe 200 }
                }
            }

            test("다수의 place 검색 요청이 주어지면") {
                val threadCount = 3
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
}
