package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.dtos.RadioStation
import com.kineton.automotive.sdk.network.apis.RadioStationApiService
import com.kineton.automotive.sdk.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.util.concurrent.CompletableFuture

class StationServiceImpl @Inject constructor(
    private val radioStationApiService: RadioStationApiService,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : StationService {

    private val scope = CoroutineScope(dispatcher)


    override fun retrieveStationsAsync(): CompletableFuture<List<RadioStation>?> =
        scope.future {
            retrieveRadioStations()
        }

    override fun retrieveRadioStationByRpIdAsync(rpId: String): CompletableFuture<RadioStation?> =
        scope.future {
            retrieveRadioStationByRpId(rpId)
        }


    private suspend fun retrieveRadioStations(): List<RadioStation> = withContext(dispatcher) {
        val response = radioStationApiService.getAllRadioStations().execute()
        if (response.isSuccessful) {
            response.body()?.data ?: throw RuntimeException("Empty response body")
        } else {
            throw RuntimeException("Request failed with code ${response.code()}")
        }
    }


    private suspend fun retrieveRadioStationByRpId(rpId: String): RadioStation = withContext(dispatcher) {
        val response = radioStationApiService.getRadioStationsByRpId(rpId).execute()
        if (response.isSuccessful) {
            response.body()?.data[0] ?: throw RuntimeException("Empty response body")
        } else {
            throw RuntimeException("Request failed with code ${response.code()}")
        }
    }
}
