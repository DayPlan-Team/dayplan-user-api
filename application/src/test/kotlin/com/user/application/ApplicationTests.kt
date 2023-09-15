package com.user.application

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ApplicationTestConfiguration::class])
class ApplicationTests {

    @Test
    fun contextLoads() {
    }

}
