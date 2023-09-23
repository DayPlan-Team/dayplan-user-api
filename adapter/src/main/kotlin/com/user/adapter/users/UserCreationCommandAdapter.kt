package com.user.adapter.users

import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.application.port.out.UserCreationCommandPort
import com.user.domain.user.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserCreationCommandAdapter(
    private val userEntityRepository: UserEntityRepository,
) : UserCreationCommandPort {
    override fun save(user: User): Long {

        val userEntity = UserEntity(
            id = user.userId,
            email = user.email,
            nickName = user.nickName,
            userAccountStatus = user.userAccountStatus,
            isVerified = user.isVerified,
        )

        return userEntityRepository.save(userEntity).id
    }
}