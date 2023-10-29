package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/user/health")
    fun checkHealth(): ResponseEntity<HealthCheck> {
        log.info("health check")
        return ResponseEntity.ok(HealthCheck(true))
    }

    data class HealthCheck(
        @JsonProperty("status") val status: Boolean,
    )

    companion object : Logger()

}