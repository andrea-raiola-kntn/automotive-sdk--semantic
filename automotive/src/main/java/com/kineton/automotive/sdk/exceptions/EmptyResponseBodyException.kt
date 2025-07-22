package com.kineton.automotive.sdk.exceptions

import java.lang.RuntimeException

class EmptyResponseBodyException(message: String = "Empty response body") : RuntimeException(message)
