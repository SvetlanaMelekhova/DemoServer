package com.example

import com.example.models.ApiResponse
import com.example.repository.HeroRepository
import com.example.repository.NEXT_PAGE_KEY
import com.example.repository.PREVIOUS_PAGE_KEY
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

const val NEXT_PAGE_KEY = "nextPage"
const val PREVIOUS_PAGE_KEY = "prevKey"

class ApplicationTest {

    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access root endpoint, asset correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = "Welcome to Boruto API!",
                    actual = response.content
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes").apply {
                val pages = 1..5
                val heroes = listOf(
                    heroRepository.page1,
                    heroRepository.page2,
                    heroRepository.page3,
                    heroRepository.page4,
                    heroRepository.page5,
                )
                pages.forEach { page ->
                    handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                        println("CURRENT PAGE: $page")
                        assertEquals(
                            expected = HttpStatusCode.OK,
                            actual = response.status()
                        )
                        val expected = ApiResponse(
                            success = true,
                            message = "ok",
                            prevPage = calculatePage(page)[PREVIOUS_PAGE_KEY],
                            nextPage = calculatePage(page)[NEXT_PAGE_KEY],
                            heroes = heroes[page - 1]
                        )
                        val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                        println("PREV PAGE: ${calculatePage(page)[PREVIOUS_PAGE_KEY]}")
                        println("NEXT PAGE: ${calculatePage(page)[NEXT_PAGE_KEY]}")
                        println("HEROES: ${heroes[page - 1]}")
                        assertEquals(
                            expected = expected,
                            actual = actual
                        )
                    }
                }
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=6").apply {
                assertEquals(
                    expected = HttpStatusCode.NotFound,
                    actual = response.status()
                )

                val expected = ApiResponse(
                    success = false,
                    message = "Heroes Not Found."
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                println("EXPECTED: $expected")
                println("ACTUAL: $actual")
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=invalid").apply {
                assertEquals(
                    expected = HttpStatusCode.BadRequest,
                    actual = response.status()
                )

                val expected = ApiResponse(
                    success = false,
                    message = "Only Numbers Allowed."
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                println("EXPECTED: $expected")
                println("ACTUAL: $actual")
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page
        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..5) {
            prevPage = prevPage?.minus(1)
        }
        if (page == 1) {
            prevPage = null
        }
        if (page == 5) {
            nextPage = null
        }
        return mapOf(PREVIOUS_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }
}