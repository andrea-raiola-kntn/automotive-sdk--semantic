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
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

/**
 * Implementation of [StationService] responsible for retrieving radio station data
 * from a remote API using coroutines and returning it asynchronously via [CompletableFuture].
 *
 * This class handles both successful and failed API responses, throwing
 * [RequestFailedException] or returning `null` when the response body is empty.
 *
 * @property radioStationApiService Service used to perform API calls for radio stations.
 * @property dispatcher Coroutine dispatcher used to offload network requests to the appropriate thread.
 * @constructor Creates a new instance of [StationServiceImpl] with the provided API service and dispatcher.
 */
class StationServiceImpl @Inject constructor(
    private val radioStationApiService: RadioStationApiService,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : StationService {

    companion object {
        private const val TAG = "StationService"
    }

    private val scope = CoroutineScope(dispatcher)

    /**
     * Retrieves a list of available [RadioStation]s asynchronously.
     *
     * If the response body is empty, a warning is logged and `null` is returned.
     * If the request fails, a [RequestFailedException] is thrown.
     *
     * @return A [CompletableFuture] containing the list of [RadioStation]s, or `null` if the body is empty.
     * @throws RequestFailedException if the API request fails.
     */
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

    /**
     * Retrieves a specific [RadioStation] asynchronously based on the provided RP ID.
     *
     * If the response body is empty, a warning is logged and `null` is returned.
     * If the request fails, a [RequestFailedException] is thrown.
     *
     * @param rpId The RP ID used to query the radio station.
     * @return A [CompletableFuture] containing the [RadioStation], or `null` if the body is empty.
     * @throws RequestFailedException if the API request fails.
     */
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


    /**
     * Performs the actual API call to retrieve all [RadioStation]s.
     *
     * @return A list of [RadioStation]s.
     * @throws EmptyResponseBodyException if the API response body is empty.
     * @throws RequestFailedException if the API request fails (non-2xx response).
     */
    @Throws(EmptyResponseBodyException::class, RequestFailedException::class)
    private suspend fun retrieveRadioStations(): List<RadioStation> = withContext(dispatcher) {
        val response = radioStationApiService.getAllRadioStations().execute()
        if (response.isSuccessful) {
            response.body()?.data ?: throw EmptyResponseBodyException()
        } else {
            throw RequestFailedException(response.code())
        }
    }


    /**
     * Performs the actual API call to retrieve a [RadioStation] by the given RP ID.
     *
     * @param rpId The RP ID to query.
     * @return The corresponding [RadioStation].
     * @throws EmptyResponseBodyException if the API response body is empty.
     * @throws RequestFailedException if the API request fails (non-2xx response).
     */
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
