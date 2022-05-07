package com.example.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging

fun Application.configureMonitoring(){
    install(CallLogging)
}