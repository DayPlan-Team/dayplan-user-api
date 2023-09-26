package com.user.adapter.location

import com.user.adapter.client.NaverGeocodeClient
import com.user.application.port.out.GeocodeMapPort
import com.user.application.request.GeocodeRequest
import com.user.application.response.GeocodeResponse
import com.user.application.response.GeocodingArea
import com.user.application.response.GeocodingCode
import com.user.application.response.GeocodingCoords
import com.user.application.response.GeocodingCoordsCenter
import com.user.application.response.GeocodingRegion
import com.user.application.response.GeocodingResults
import com.user.application.response.GeocodingStatus
import com.user.util.Logger
import org.springframework.stereotype.Component

@Component
class NaverGeocodeMapAdapter(
    private val naverGeocodeClient: NaverGeocodeClient,
) : GeocodeMapPort {

    val defaultResponse = GeocodeResponse(
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

        try {
            val response = naverGeocodeClient.getGeocodeResponse("${geocodeRequest.longitude},${geocodeRequest.latitude}").execute()
            if (response.isSuccessful) {
                return response.body() ?: defaultResponse
            }
        } catch (e: Exception) {
            return defaultResponse
        }

        return defaultResponse
    }

    companion object : Logger() {
        const val GEOCODING_RESPONSE_CODE = -9999
        const val GEOCODING_RESPONSE_NAME = "response-error"
        const val GEOCODING_RESPONSE_MESSAGE = "default"
    }
}