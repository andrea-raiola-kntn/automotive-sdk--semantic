package com.kineton.automotive.sdk.network

import java.io.File
import java.nio.file.Paths
import java.util.Properties

object RadioplayerCredentials {
        private val props: Properties by lazy {
            val p = Properties()

            val workingDir = Paths.get("").toAbsolutePath().toString()

            var dir: File? = File(workingDir)
            var file: File? = null

            while (dir != null) {
                val candidate = File(dir, "local.properties")
                if (candidate.exists()) {
                    file = candidate
                    break
                }
                dir = dir.parentFile
            }

            if (file != null) {
                println("✅ Found local.properties: ${file.absolutePath}")
                p.load(file.inputStream())
            } else {
                println("❌ local.properties not found in: $workingDir")
            }

            p
        }

    private fun getEnvOrProp(key: String): String {
        return System.getenv(key)
            ?: props.getProperty(key)
            ?: error("Missing required credentias: $key")
    }

    val username: String get() = getEnvOrProp("RADIOPLAYER_USERNAME")
    val password: String get() = getEnvOrProp("RADIOPLAYER_PASSWORD")
}
