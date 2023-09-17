package com.user.domain.user.config

import com.user.domain.user.usecase.UserCreationUseCase
import com.user.domain.user.usecase.UserCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun applyUserCreationUseCase(): UserCreationUseCase {
        return UserCreator()
    }
}