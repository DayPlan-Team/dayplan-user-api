package com.user.application.service

import com.user.adapter.location.entity.PlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.adapter.location.persistence.UserStayedPlaceEntityRepository
import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.application.ApplicationTestConfiguration
import com.user.application.request.PlaceApiRequest
import com.user.domain.location.Place
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
    @Autowired private val userStayedPlaceEntityRepository: UserStayedPlaceEntityRepository,
    @Autowired private val placeEntityRepository: PlaceEntityRepository,
    @Autowired private val userEntityRepository: UserEntityRepository,
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf


    given("유저가 등록하고자 하는 위치가 이미 저장되어 있어요") {
        val user = User(
            email = "sheinKo@naver.com",
            userAccountStatus = UserAccountStatus.NORMAL,
            isVerified = true,
            mandatoryTermsAgreed = true,
            nickName = "GoseKose",
            userId = 1L,
        )

        val place = Place(
            placeName = "스타벅스",
            placeCategory = PlaceCategory.CAFE,
            latitude = 127.033234,
            longitude = 37.3232323,
            address = "서울특별시 스프링로 코틀린대로 47",
            roadAddress = "서울특별시 스프링구 코틀린동 37",
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


        val savePlace = placeEntityRepository.save(
            PlaceEntity.fromPlace(place)
        ).toPlace()

        val saveUser = userEntityRepository.save(
            UserEntity.from(user)
        ).toUser()

        `when`("위치 저장 요청을 보내요") {
            userStayedPlaceService.upsertUserStayedPlace(
                user = saveUser,
                place = savePlace,
                placeApiRequest = placeApiRequest,
            )

            val userStayedPlaceResult = userStayedPlaceEntityRepository.findAll()
                .map { it.placeUserDescription }
                .first()

            then("새로운 플레이스에 대한 위치와 유저의 플레이스가 저장되어야 해요") {
                userStayedPlaceResult shouldBe placeApiRequest.placeUserDescription
            }
        }
    }
})
