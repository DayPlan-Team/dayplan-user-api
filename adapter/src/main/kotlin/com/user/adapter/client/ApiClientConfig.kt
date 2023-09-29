package com.user.adapter.client

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ApiClientConfig {

    @Value("\${naver.map-client-id}")
    private lateinit var naverOpenApiClientId: String

    @Value("\${naver.map-client-secret}")
    private lateinit var naverOpenApiClientSecret: String


    @Value("\${naver.develop-client-id}")
    private lateinit var naverDevelopClientId: String

    @Value("\${naver.develop-client-secret}")
    private lateinit var naverDevelopClientSecret: String


    @Bean
    fun applyGoogleAccountClient(): GoogleAccountClient {
        return Retrofit.Builder()
            .baseUrl("https://oauth2.googleapis.com")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleAccountClient::class.java)
    }

    @Bean
    fun applyNaverGeocodeClient(): NaverGeocodeClient {
        return Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com")
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originRequest = chain.request()
                    val newRequest = originRequest.newBuilder()
                        .header(NAVER_OPEN_API_KEY_ID, naverOpenApiClientId)
                        .header(NAVER_OPEN_API_KEY_SECRET, naverOpenApiClientSecret)
                        .build()
                    chain.proceed(newRequest)
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverGeocodeClient::class.java)
    }

    @Bean
    fun applyNaverSearch(): NaverSearchClient {
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originRequest = chain.request()
                    val newRequest = originRequest.newBuilder()
                        .header(NAVER_CLIENT_ID, naverDevelopClientId)
                        .header(NAVER_CLIENT_SECRET, naverDevelopClientSecret)
                        .build()
                    chain.proceed(newRequest)
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverSearchClient::class.java)
    }


    companion object {
        const val NAVER_OPEN_API_KEY_ID = "X-NCP-APIGW-API-KEY-ID"
        const val NAVER_OPEN_API_KEY_SECRET = "X-NCP-APIGW-API-KEY"
        const val NAVER_CLIENT_ID = "X-Naver-Client-Id"
        const val NAVER_CLIENT_SECRET = "X-Naver-Client-Secret"
    }
}