package com.user.adapter.users

import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserCommandAdapter(
    private val userEntityRepository: UserEntityRepository,
) : UserCommandPort {
    override fun save(user: User): User {
        return userEntityRepository.save(UserEntity.from(user)).toDomainModel()
    }
}
