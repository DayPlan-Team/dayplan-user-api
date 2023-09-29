package com.user.adapter.location

import com.user.adapter.client.NaverSearchClient
import com.user.application.port.out.LocationSearchPort
import com.user.util.Logger
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class NaverLocationSearchAdapter(
    private val naverSearchClient: NaverSearchClient,
) : LocationSearchPort {
    override fun searchLocation(query: String) {
        val encode = URLEncoder.encode(query, UTF_8)
        log.info("encode = $encode")
        val location = naverSearchClient.searchLocation(encode)

        val response = location.execute()
        if (response.isSuccessful) {
            log.info("response = ${response.body()}")
        }
    }

    companion object : Logger() {
        const val UTF_8 = "utf-8"
    }

}