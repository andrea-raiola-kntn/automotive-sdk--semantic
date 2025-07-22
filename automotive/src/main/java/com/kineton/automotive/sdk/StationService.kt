package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.dtos.RadioStation
import java.util.concurrent.CompletableFuture

interface StationService {
    fun retrieveStationsAsync(): CompletableFuture<List<RadioStation>?>
    fun retrieveRadioStationByRpIdAsync(rpId: String): CompletableFuture<RadioStation?>
}
