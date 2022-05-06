package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module(){
    routing {
        get("/") {
            call.respondText ("Hello, World!")
        }
    }
}

@Suppress("unused")
fun Application.module2(){
    routing {
        get("/book") {
            call.respondText ("Hello, World2!")
        }
    }
}
