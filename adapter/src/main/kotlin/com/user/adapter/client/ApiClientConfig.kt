package com.user.adapter.client

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
            .baseUrl(GOOGLE_LOGIN_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleAccountClient::class.java)
    }

    @Bean
    fun applyNaverGeocodeClient(): NaverGeocodeClient {
        return Retrofit.Builder()
            .baseUrl(NAVER_GEOCODE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originRequest = chain.request()
                    val newRequest = originRequest.newBuilder()
                        .header(NAVER_OPEN_API_KEY_ID, naverOpenApiClientId)
                        .header(NAVER_OPEN_API_KEY_SECRET, naverOpenApiClientSecret)
                        .build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverGeocodeClient::class.java)
    }

    @Bean
    fun applyNaverSearch(): NaverSearchClient {
        return Retrofit.Builder()
            .baseUrl(NAVER_OPEN_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originRequest = chain.request()
                    val newRequest = originRequest.newBuilder()
                        .header(NAVER_CLIENT_ID, naverDevelopClientId)
                        .header(NAVER_CLIENT_SECRET, naverDevelopClientSecret)
                        .build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverSearchClient::class.java)
    }

    @Profile("!prod")
    @Bean
    fun applyDateCourseStayCheck(): DateCourseClient {
        return Retrofit.Builder()
            .baseUrl(CONTENT_SERVER_DEV_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DateCourseClient::class.java)
    }


    companion object {
        const val NAVER_OPEN_API_KEY_ID = "X-NCP-APIGW-API-KEY-ID"
        const val NAVER_OPEN_API_KEY_SECRET = "X-NCP-APIGW-API-KEY"
        const val NAVER_CLIENT_ID = "X-Naver-Client-Id"
        const val NAVER_CLIENT_SECRET = "X-Naver-Client-Secret"

        const val GOOGLE_LOGIN_URL = "https://oauth2.googleapis.com"
        const val NAVER_GEOCODE_URL = "https://naveropenapi.apigw.ntruss.com"
        const val NAVER_OPEN_URL = "https://openapi.naver.com"

        const val CONTENT_SERVER_DEV_URL = "http://localhost:8078"
    }
}