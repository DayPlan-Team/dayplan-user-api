package com.user.adapter

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ActiveProfiles

@SpringBootApplication(scanBasePackages = ["com.user.**"])
@EnableJpaRepositories(basePackages = ["com.user.adapter.**"])
@EntityScan(basePackages = ["com.user.**"])
@ComponentScan(basePackages = ["com.user.**"])
class AdapterTestConfiguration