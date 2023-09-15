package com.user.application.service

import com.user.application.mapper.UserCreationRequestMapper
import com.user.application.port.UserAccountVerificationSenderPort
import com.user.application.port.UserCreationCommandPort
import com.user.application.request.VerifiedUserCreationRequest
import com.user.domain.usecase.UserCreationUseCase
import org.springframework.stereotype.Service

@Service
class UserCreationService(
    private val userCreationUseCase: UserCreationUseCase,
    private val userAccountVerificationSenderPort: UserAccountVerificationSenderPort,
    private val userCreationCommandPort: UserCreationCommandPort,
)