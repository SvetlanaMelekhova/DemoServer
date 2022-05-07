package com.example

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.title

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        module()
    }.start(wait = true)
}

fun Application.module() {

    routing {
        static {
            resources("static")
        }
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/welcome") {
            val name = call.request.queryParameters["name"]
            call.respondHtml {
                head {
                    title { +"Custom Title" }
                }
                body {
                    if (name.isNullOrEmpty()) {
                        h3 { +"Welcome!" }
                    } else {
                        h3 { +"Welcome, $name!" }
                    }
                    p { +"Current directory is: ${System.getProperty("user.dir")}" }
                    img(src = "logo.jpg") {  }
                }
            }
        }
    }
}


