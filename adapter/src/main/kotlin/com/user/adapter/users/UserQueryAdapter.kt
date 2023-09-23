package com.user.adapter.users

import com.user.adapter.users.persistence.UserEntityRepository
import com.user.application.port.out.UserQueryPort
import com.user.domain.user.User
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class UserQueryAdapter(
    private val userEntityRepository: UserEntityRepository,
) : UserQueryPort {
    override fun findUserByUserId(userId: Long): User {
        return userEntityRepository.findById(userId)
            .orElseThrow { UserException(UserExceptionCode.NOT_FOUND_USER) }
            .toUser()
    }

    override fun findUserByEmailOrNull(email: String): User? {
        return userEntityRepository.findByEmail(email)?.toUser()
    }
}