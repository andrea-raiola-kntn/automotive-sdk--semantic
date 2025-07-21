package com.kineton.automotive.sdk

import java.util.concurrent.CompletableFuture

interface StationService {
    fun retrieveStationsAsync(): CompletableFuture<String>
}
