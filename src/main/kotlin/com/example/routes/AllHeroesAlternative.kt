package com.example.routes

import com.example.models.ApiResponse
import com.example.repository.HeroRepository
import com.example.repository.HeroRepositoryAlternative
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import org.koin.ktor.ext.inject

fun Route.getAllHeroesAlternative() {

    val heroRepositoryAlternative: HeroRepositoryAlternative by inject()

    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 3

            val apiResponse = heroRepositoryAlternative.getAllHeroes(
                page = page, limit = limit
            )
            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only Numbers Allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Heroes not Found."),
                status = HttpStatusCode.NotFound
            )
        }
    }
}