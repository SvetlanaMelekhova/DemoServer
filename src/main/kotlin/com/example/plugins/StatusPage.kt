package com.example.plugins

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import javax.naming.AuthenticationException

fun Application.configureStatusPages(){
    install(StatusPages){
        status(HttpStatusCode.NotFound){
            call.respond(
                message = "Page Not Found.",
                status = HttpStatusCode.NotFound
            )
        }
        exception<AuthenticationException> {
            call.respond(
               message = "We caught an exception.",
               status = HttpStatusCode.OK
            )
        }
    }
}