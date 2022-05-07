package com.example.plugins

import com.example.routes.root
import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing

fun Application.configureRouting() {
    routing {
        root()
    }
}