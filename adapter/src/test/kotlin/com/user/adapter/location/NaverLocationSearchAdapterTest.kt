package com.user.adapter.location

import com.user.adapter.AdapterTestConfiguration
import io.kotest.core.spec.style.FunSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [AdapterTestConfiguration::class])
class NaverLocationSearchAdapterTest(
    @Autowired private val naverPlaceSearchAdapter: NaverPlaceSearchAdapter,
) : FunSpec({


    context("테스트1") {
        val query = "강동구 성내동 까치 부동산"
        test("테스트2") {
            naverPlaceSearchAdapter.searchLocation(query, 1)
        }
    }

})
