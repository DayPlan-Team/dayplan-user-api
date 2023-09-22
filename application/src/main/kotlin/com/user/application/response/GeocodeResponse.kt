package com.user.application.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GeocodeResponse(
    @JsonProperty("status") val status: GeocodingStatus,
    @JsonProperty("results") val results: List<GeocodingResults>,
)

data class GeocodingStatus(
    @JsonProperty("code") val code: Int = 0,
    @JsonProperty("name") val name: String = "",
    @JsonProperty("message") val message: String = "",
)

data class GeocodingResults(
    @JsonProperty("name") val name: String = "",
    @JsonProperty("code") val code: GeocodingCode,
    @JsonProperty("region") val region: GeocodingRegion,
)
data class GeocodingCode(
    @JsonProperty("id") val id: String = "",
    @JsonProperty("type") val type: String = "",
    @JsonProperty("mappingId") val mappingId: String = "",
)

data class GeocodingRegion(
    @JsonProperty("area0") val area0: GeocodingArea,
    @JsonProperty("area1") val area1: GeocodingArea,
    @JsonProperty("area2") val area2: GeocodingArea,
    @JsonProperty("area3") val area3: GeocodingArea,
)

data class GeocodingArea(
    @JsonProperty("name") val name: String = "",
    @JsonProperty("coords") val coords: GeocodingCoords,
)

data class GeocodingCoords(
    @JsonProperty("center") val center: GeocodingCoordsCenter,
)

data class GeocodingCoordsCenter(
    @JsonProperty("crs") val crs: String = "",
    @JsonProperty("x") val longitude: Double = 0.0,
    @JsonProperty("y") val latitude: Double = 0.0,
)