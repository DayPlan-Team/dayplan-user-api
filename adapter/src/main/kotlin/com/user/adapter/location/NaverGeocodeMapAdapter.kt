package com.user.adapter.location

import com.user.application.port.out.GeocodeMapPort
import com.user.application.request.GeocodeRequest
import com.user.application.response.GeocodingArea
import com.user.application.response.GeocodingCode
import com.user.application.response.GeocodingCoords
import com.user.application.response.GeocodingCoordsCenter
import com.user.application.response.GeocodingRegion
import com.user.application.response.GeocodeResponse
import com.user.application.response.GeocodingResults
import com.user.application.response.GeocodingStatus
import com.user.util.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NaverGeocodeMapAdapter(
    private val webClient: WebClient,
) : GeocodeMapPort {

    @Value("\${naver.map-client-id}")
    private lateinit var clientId: String

    @Value("\${naver.map-client-secret}")
    private lateinit var secretKey: String

    val defaultResponse =  GeocodeResponse(
        status = GeocodingStatus(
            code = GEOCODING_RESPONSE_CODE,
            name = GEOCODING_RESPONSE_NAME,
            message = GEOCODING_RESPONSE_MESSAGE,
        ),
        results = listOf(
            GeocodingResults(
                name = "legalcode",
                code = GeocodingCode(
                    id = "1165010800",
                    type = "L",
                    mappingId = "09650108",
                ),
                region = GeocodingRegion(
                    area0 = GeocodingArea(
                        name = "kr",
                        coords = GeocodingCoords(
                            center = GeocodingCoordsCenter(
                                crs = "",
                                longitude = 0.0,
                                latitude = 0.0,
                            )
                        )
                    ),
                    area1 = GeocodingArea(
                        name = "서울특별시",
                        coords = GeocodingCoords(
                            center = GeocodingCoordsCenter(
                                crs = "EPSG:4326",
                                longitude = 126.978386,
                                latitude = 37.56661,
                            )
                        ),
                    ),
                    area2 = GeocodingArea(
                        name = "서초구",
                        coords = GeocodingCoords(
                            center = GeocodingCoordsCenter(
                                crs = "EPSG:4326",
                                longitude = 127.032,
                                latitude = 37.48357,
                            )
                        ),
                    ),
                    area3 = GeocodingArea(
                        name = "서초동",
                        coords = GeocodingCoords(
                            center = GeocodingCoordsCenter(
                                crs = "EPSG:4326",
                                longitude = 127.01951,
                                latitude = 37.49012,
                            )
                        ),
                    ),
                    area4 = GeocodingArea(
                        name = "서초동",
                        coords = GeocodingCoords(
                            center = GeocodingCoordsCenter(
                                crs = "EPSG:4326",
                                longitude = 127.01951,
                                latitude = 37.49012,
                            )
                        ),
                    ),
                )
            ),
        )
    )


    override fun getGeoCodingResponse(geocodeRequest: GeocodeRequest): GeocodeResponse {
        log.info("reverseGeoCode = ${reverseGeoCode(geocodeRequest).toString()}")


        return try {
            reverseGeoCode(geocodeRequest) ?: defaultResponse
        } catch (e : Exception) {
            defaultResponse
        }
    }

    private fun reverseGeoCode(coordinates: GeocodeRequest): GeocodeResponse? {
        return webClient.get()
            .uri(NAVER_OPEN_API_REVERSE_COORDINATE + "coords=${coordinates.longitude},${coordinates.latitude}")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(API_KEY_ID, clientId)
            .header(API_KEY_SECRET, secretKey)
            .retrieve()
            .bodyToMono(GeocodeResponse::class.java)
            .block()
    }

    companion object : Logger() {
        const val NAVER_OPEN_API_REVERSE_COORDINATE =
            "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&"
        const val API_KEY_ID = "X-NCP-APIGW-API-KEY-ID"
        const val API_KEY_SECRET = "X-NCP-APIGW-API-KEY"
        const val GEOCODING_RESPONSE_CODE = -9999
        const val GEOCODING_RESPONSE_NAME = "response-error"
        const val GEOCODING_RESPONSE_MESSAGE = "default"
    }
}