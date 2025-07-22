package com.kineton.automotive.sdk.dtos

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class RadioplayerResponse<T>(
    @param:JsonProperty("data", required = true)
    @param:JsonAlias("services", "categories")
    val data: T
)
