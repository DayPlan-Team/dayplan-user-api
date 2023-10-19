package com.user.api.grpc

import com.user.util.Logger
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Server
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.concurrent.TimeUnit


@Profile("default | local")
@Configuration
class GrpcLocalConfig {

    private var grpcServer: Server? = null

    /* 운영 배포시 address 설정 필요함 */
    @Bean
    fun userManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()
    }

    @Bean
    fun contentManagedChannel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", 50052)
            .usePlaintext()
            .build()
    }

    @PreDestroy
    fun destroy() {
        userManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
        contentManagedChannel().shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    companion object : Logger()
}