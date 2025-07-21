package com.kineton.automotive.sdk

import android.util.Log
import com.kineton.automotive.sdk.network.NetworkClient
import com.kineton.automotive.sdk.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.util.concurrent.CompletableFuture

class StationServiceImpl @Inject constructor(
    private val networkClient: NetworkClient,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : StationService {

    override fun retrieveStationsAsync(): CompletableFuture<String> {
        return CoroutineScope(dispatcher).future {
            retrieveStations()
        }
    }

    private suspend fun retrieveStations(): String = withContext(dispatcher) {
        networkClient.getCall("/380/metadata/api/v5/services?imgWidth=600").use { response ->
            val bodyString = response.body.string()
            Log.d("response", bodyString)
            bodyString
        }
    }
}
