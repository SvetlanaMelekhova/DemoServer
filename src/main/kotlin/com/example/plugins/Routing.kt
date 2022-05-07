package com.example.plugins

import com.example.routes.getAllHeroes
import com.example.routes.getSearchHero
import com.example.routes.root
import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.get
import io.ktor.routing.routing
import javax.naming.AuthenticationException

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
        getSearchHero()

        static("/images"){
            resources("images")
        }
    }
}