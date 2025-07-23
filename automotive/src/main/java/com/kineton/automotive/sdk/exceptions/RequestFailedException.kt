package com.kineton.automotive.sdk.exceptions

class RequestFailedException(
    val statusCode: Int,
    message: String = "Request failed with code $statusCode"
) : RuntimeException(message)
