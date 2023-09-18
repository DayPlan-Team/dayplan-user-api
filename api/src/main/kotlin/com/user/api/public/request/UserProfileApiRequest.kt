package com.user.api.public.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserProfileApiRequest(
    @JsonProperty("nick_name") val nickName: String,
)
