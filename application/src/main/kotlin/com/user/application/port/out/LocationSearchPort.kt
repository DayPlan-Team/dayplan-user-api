package com.user.application.port.out

import org.springframework.stereotype.Component

@Component
interface LocationSearchPort {

    fun searchLocation(query: String)

}