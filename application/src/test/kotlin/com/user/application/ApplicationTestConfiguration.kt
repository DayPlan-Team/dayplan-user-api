package com.user.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.user.**"])
@EnableJpaRepositories(basePackages = ["com.user.adapter.**"])
@EntityScan(basePackages = ["com.user.**"])
@ComponentScan(basePackages = ["com.user.**"])
class ApplicationTestConfiguration