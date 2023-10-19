package com.user.domain.location.port

import com.fasterxml.jackson.annotation.JsonProperty

data class PlacePortItemResponse(
    @JsonProperty("lastBuildDate") val lastBuildDate: String = "",
    @JsonProperty("total") val total: Int = 0,
    @JsonProperty("start") val start: Int = 1,
    @JsonProperty("display") val display: Int = 10,
    @JsonProperty("items") val items: List<PlaceItem> = emptyList(),
)

data class PlaceItem(
    @JsonProperty("title") val title: String = "",
    @JsonProperty("link") val link: String = "",
    @JsonProperty("category") val category: String = "",
    @JsonProperty("description") val description: String = "",
    @JsonProperty("telephone") val telephone: String = "",
    @JsonProperty("address") val address: String = "",
    @JsonProperty("roadAddress") val roadAddress: String = "",
    @JsonProperty("mapx") val mapx: String = "",
    @JsonProperty("mapy") val mapy: String = "",
)
