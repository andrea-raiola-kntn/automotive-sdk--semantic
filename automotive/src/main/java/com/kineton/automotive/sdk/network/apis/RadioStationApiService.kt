package com.kineton.automotive.sdk.network.apis

import com.kineton.automotive.sdk.dtos.RadioStation
import com.kineton.automotive.sdk.dtos.RadioplayerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RadioStationApiService {
    // TODO country code need to be saved in MMKV
    @GET("/380/metadata/api/v5/services")
    fun getAllRadioStations(): Call<RadioplayerResponse<List<RadioStation>>>

    // TODO country code need to be saved in MMKV
    @GET("/380/metadata/api/v2/services/{rpId}")
    fun getRadioStationsByRpId(
        @Path("rpId"
        ) rpId: String): Call<RadioplayerResponse<List<RadioStation>>>
}