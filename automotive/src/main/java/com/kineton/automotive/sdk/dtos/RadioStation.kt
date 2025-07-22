package com.kineton.automotive.sdk.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class RadioStation(
    @param:JsonProperty("id")
    val id: String,

    @param:JsonProperty("name")
    val name: String,

    @param:JsonProperty("description")
    val description: String,

    @param:JsonProperty("alphanumericKey")
    val alphanumericKey: String?,

    @param:JsonProperty("groupName")
    val groupName: String,

    @param:JsonProperty("colour")
    val colour: String,
)
