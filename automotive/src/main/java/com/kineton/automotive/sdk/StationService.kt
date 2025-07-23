package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.dtos.RadioStation
import java.util.concurrent.CompletableFuture

/**
 * Service interface for retrieving radio station data asynchronously.
 *
 * Provides methods to fetch all available radio stations or a single radio station
 * identified by its RP ID.
 */
interface StationService {
    /**
     * Asynchronously retrieves a list of all available [RadioStation]s.
     *
     * @return A [CompletableFuture] that completes with a list of [RadioStation]s,
     *         or `null` if the response body is empty.
     */
    fun retrieveStationsAsync(): CompletableFuture<List<RadioStation>?>

    /**
     * Asynchronously retrieves a [RadioStation] using the provided RP ID.
     *
     * @param rpId The RP ID of the radio station to retrieve.
     * @return A [CompletableFuture] that completes with the matching [RadioStation],
     *         or `null` if the response body is empty.
     */
    fun retrieveRadioStationByRpIdAsync(rpId: String): CompletableFuture<RadioStation?>
}
