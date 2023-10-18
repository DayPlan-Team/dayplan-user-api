package com.user.application.service

import com.ninjasquad.springmockk.MockkBean
import com.user.application.ApplicationTestConfiguration
import com.user.application.port.out.PlacePort
import com.user.application.port.out.PlaceSearchPort
import com.user.application.response.PlaceItem
import com.user.application.response.PlacePortItemResponse
import com.user.util.address.PlaceCategory
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@ActiveProfiles("test")
@SpringBootTest(classes = [ApplicationTestConfiguration::class])
class PlaceSearchServiceTest {

    @Autowired
    private lateinit var placeSearchService: PlaceSearchService

    @MockkBean
    private lateinit var placeSearchPort: PlaceSearchPort

    val placeItems = listOf(
        PlaceItem(
            title = "B",
            link = "www.b.com",
            category = "카페",
            description = "카페",
            telephone = "010-0000-1111",
            address = "중랑구A",
            roadAddress = "중랑구A",
            mapx = "123001002",
            mapy = "3701002",
        ),
        PlaceItem(
            title = "B",
            link = "www.b.com",
            category = "카페",
            description = "카페",
            telephone = "010-0000-1112",
            address = "중랑구B",
            roadAddress = "중랑구B",
            mapx = "123001003",
            mapy = "3701003",
        ),
    )

    @Test
    fun searchLocationDistributeTest() {

        /* give */
        /* 외부 의존성을 모킹해요 */
        every { placeSearchPort.searchLocation(any(), any()) } returns PlacePortItemResponse(
            lastBuildDate = "",
            total = 3,
            start = 1,
            display = 3,
            items = placeItems,
        )

        /* when */
        /* 멀티 스레드로 place 검색 요청을 수행해 */
        val threadCount = 10
        val latch = CountDownLatch(threadCount)
        val executorService = Executors.newFixedThreadPool(32)

        repeat(threadCount) {
            executorService.submit {
                try {
                    placeSearchService.searchPlace(
                        place = "중랑구",
                        placeCategory = PlaceCategory.ACTIVITY,
                        start = 1,
                        administrativeCategoryId = "1234"
                    )
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()


        val result = placeSearchService.searchPlace(
            place = "중랑구",
            placeCategory = PlaceCategory.ACTIVITY,
            start = 1,
            administrativeCategoryId = "1234"
        )

        /* then */
        /* place 개수는 A, B 2개만 조회되어야 해요 */
        result.size shouldBe 2L
    }
}