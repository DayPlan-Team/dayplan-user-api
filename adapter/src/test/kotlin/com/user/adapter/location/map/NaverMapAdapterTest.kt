package com.user.adapter.location.map

import com.user.application.request.GeocodeRequest
import com.user.util.Logger
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class NaverMapAdapterTest(
    @Autowired private val naverMapAdapter: NaverGeocodeMapAdapter,
) : BehaviorSpec({

    val log = Logger().log

    given("위도와 경도가 주어져요") {
        val coordinates = GeocodeRequest(36.5929071, 127.2923750)

        `when`("네이버 맵으로 주소 요청을 보내면") {
            val result = naverMapAdapter.getGeoCodingResponse(coordinates)

            then("요청 결과를 받을 수 있어요") {
                log.info("result = $result")
            }
        }
    }

    given("잘못된 위도와 경도가 주어져요") {
        val coordinates = GeocodeRequest(-137.5038061, 127.0241492)

        `when`("네이버 맵으로 주소 요청을 보내면") {
            val result = naverMapAdapter.getGeoCodingResponse(coordinates)

            then("요청 결과를 받지만 default로 설정한 결과를 받아요") {
                log.info("result = $result")
                result.status.code shouldBe NaverGeocodeMapAdapter.GEOCODING_RESPONSE_CODE
                result.status.name shouldBe NaverGeocodeMapAdapter.GEOCODING_RESPONSE_NAME
                result.status.message shouldBe NaverGeocodeMapAdapter.GEOCODING_RESPONSE_MESSAGE
            }
        }
    }

})
