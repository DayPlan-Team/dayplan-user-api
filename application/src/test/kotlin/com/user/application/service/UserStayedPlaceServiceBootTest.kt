package com.user.application.service

import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.adapter.location.persistence.UserStayedPlaceEntityRepository
import com.user.application.ApplicationTestConfiguration
import com.user.application.request.PlaceRequest
import com.user.domain.location.PlaceCategory
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [ApplicationTestConfiguration::class])
class UserStayedPlaceServiceBootTest(
    @Autowired private val userStayedPlaceService: UserStayedPlaceService,
    @Autowired private val placeEntityRepository: PlaceEntityRepository,
    @Autowired private val userStayedPlaceEntityRepository: UserStayedPlaceEntityRepository,
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    given("유저 한 명이 머문 위치 등록을 요청해요") {
        val user = User(
            email = "sheinKo@naver.com",
            userAccountStatus = UserAccountStatus.NORMAL,
            isVerified = true,
            mandatoryTermsAgreed = true,
            nickName = "GoseKose",
            userId = 1L,
        )

        val placeRequest = PlaceRequest(
            placeName = "스타벅스",
            placeCategory = PlaceCategory.CAFE,
            latitude = 127.033234,
            longitude = 37.3232323,
            address = "서울특별시 스프링로 코틀린대로 47",
            roadAddress = "서울특별시 스프링구 코틀린동 37",
            placeUserDescription = "스프링구에 있는 스타벅스",
        )

        `when`("위치 저장 요청을 보내요") {
            userStayedPlaceService.upsertUserStayedPlace(
                user = user,
                placeRequest = placeRequest,
            )

            val savedPlaceResult = placeEntityRepository.findAll()
                .map { it.toPlace() }

            val userStayedPlaceResult = userStayedPlaceEntityRepository.findAll()
                .map { it.placeId }

            then("새로운 플레이스에 대한 위치와 유저의 플레이스가 저장되어야 해요") {
                savedPlaceResult.size shouldBe 1L
                savedPlaceResult[0].address shouldBe "서울특별시 스프링로 코틀린대로 47"

                userStayedPlaceResult.size shouldBe 1L
                userStayedPlaceResult[0] shouldBe savedPlaceResult[0].id
            }
        }
    }

})
