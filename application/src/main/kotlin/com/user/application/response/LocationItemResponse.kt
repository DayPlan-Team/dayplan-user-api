package com.user.application.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationItemResponse(
    @JsonProperty("lastBuildDate") val lastBuildDate: String,
    @JsonProperty("total") val total: Int,
    @JsonProperty("start") val start: Int,
    @JsonProperty("display") val display: Int,
    @JsonProperty("items") val items: List<LocationItem>,
)

data class LocationItem(
    @JsonProperty("title") val title: String,
    @JsonProperty("link") val link: String,
    @JsonProperty("category") val category: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("telephone") val telephone: String,
    @JsonProperty("address") val address: String,
    @JsonProperty("roadAddress") val roadAddress: String,
    @JsonProperty("mapx") val mapx: String,
    @JsonProperty("mapy") val mapy: String,
)
