package com.user.domain.usecase.config

import com.user.domain.usecase.UserCreationUseCase
import com.user.domain.usecase.impl.UserCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun applyUserCreationUseCase(): UserCreationUseCase {
        return UserCreator()
    }
}