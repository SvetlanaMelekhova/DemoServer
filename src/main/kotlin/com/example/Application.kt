package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module(){
    install(CallLogging)
    routing {
        get("/") {
            call.respondText ("Hello, World!")
        }
    }
}