package com.user.adapter

import com.user.adapter.terms.entity.TermsEntity
import com.user.adapter.terms.persistence.TermsEntityRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local", "default")
class LocalDataInitializer(
    private val termsEntityRepository: TermsEntityRepository,
) : CommandLineRunner {

    private val termsEntities = listOf(
        TermsEntity(
            id = 1,
            sequence = 1,
            content = "서비스 이용 약관",
            mandatory = true,
        ),
        TermsEntity(
            id = 2,
            sequence = 2,
            content = "위치 정보 제공 동의",
            mandatory = true,
        ),
    )

    override fun run(vararg args: String?) {
        termsEntityRepository.saveAll(termsEntities)
    }

}