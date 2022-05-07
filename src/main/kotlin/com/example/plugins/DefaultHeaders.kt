package com.example.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpHeaders
import java.time.Duration

fun Application.configureDefaultHeaders() {
    install(DefaultHeaders){
        val oneYearInSeconds = Duration.ofDays(365).seconds
        header(
            name = HttpHeaders.CacheControl,
            value = "public, max-age=$oneYearInSeconds, immutable"
        )
    }
}