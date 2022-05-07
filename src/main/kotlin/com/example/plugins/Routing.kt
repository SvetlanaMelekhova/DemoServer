package com.example.plugins

import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {

        static("/images") {
            resources("images")
        }
    }
}