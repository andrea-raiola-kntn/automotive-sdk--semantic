package com.kineton.automotive.sdk

import android.util.Log
import com.kineton.automotive.sdk.dtos.RadioStation
import com.kineton.automotive.sdk.exceptions.EmptyResponseBodyException
import com.kineton.automotive.sdk.exceptions.RequestFailedException
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

    companion object {
        private const val TAG = "StationService"
    }

    private val scope = CoroutineScope(dispatcher)

    @Throws(RequestFailedException::class)
    override fun retrieveStationsAsync(): CompletableFuture<List<RadioStation>?> =
        scope.future {
            try {
                retrieveRadioStations()
            } catch (e: EmptyResponseBodyException) {
                Log.w(
                    TAG,
                    "Empty response body for retrieveStationsAsync()",
                    e
                )
                null
            }
        }

    @Throws(RequestFailedException::class)
    override fun retrieveRadioStationByRpIdAsync(rpId: String): CompletableFuture<RadioStation?> =
        scope.future {
            try {
                retrieveRadioStationByRpId(rpId)
            } catch (e: EmptyResponseBodyException) {
                Log.w(
                    TAG,
                    "Empty response body for retrieveStationsAsync()",
                    e
                )
                null
            }
        }


    @Throws(EmptyResponseBodyException::class, RequestFailedException::class)
    private suspend fun retrieveRadioStations(): List<RadioStation> = withContext(dispatcher) {
        val response = radioStationApiService.getAllRadioStations().execute()
        if (response.isSuccessful) {
            response.body()?.data ?: throw EmptyResponseBodyException()
        } else {
            throw RequestFailedException(response.code())
        }
    }


    @Throws(EmptyResponseBodyException::class, RequestFailedException::class)
    private suspend fun retrieveRadioStationByRpId(rpId: String): RadioStation = withContext(dispatcher) {
        val response = radioStationApiService.getRadioStationsByRpId(rpId).execute()
        if (response.isSuccessful) {
            response.body()?.data[0] ?: throw EmptyResponseBodyException()
        } else {
            throw RequestFailedException(response.code())
        }
    }
}
