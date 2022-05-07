package com.example.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.root(){
    get("/"){
        call.respond(
            message = "Welcome to Boruto API!",
            status = HttpStatusCode.OK
        )
    }
}