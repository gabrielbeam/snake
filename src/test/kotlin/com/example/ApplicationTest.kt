package com.example

import com.example.model.*
import com.example.model.anyEndGameState
import com.example.plugins.configureRouting
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testNewEndpoint() = testApplication {
        // given
        application {
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val width = 100
        val height = 100
        //when
        val response = client.get("/new?w=$width&h=$height")
        val res = response.bodyAsText()
        //println(res)
        //then

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(res, """"width":$width""")
        assertContains(res, """"height":$height""")
    }

    @Test
    fun `throws NumberFormatException exception when an float is passed in`() = testApplication {

        application {
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val width = 100.0
        val height = 100
        //when
        val response = client.get("/new?w=$width&h=$height")
        val res = response.bodyAsText()

        //then

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(res, "Width and height should be integer.")
    }

    @Test
    fun `throws InvalidMapException when a width with negative value is passed in`() = testApplication {

        application {
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val width = -10
        val height = 100
        //when
        val response = client.get("/new?w=$width&h=$height")
        val res = response.bodyAsText()

        //then

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(res, "The width and height of the map must be greater than 0.")
    }

    @Test
    fun `create a new game and validate the endGame`() = testApplication {

        application {
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val endGameState = anyEndGameState().copy(
            width = 20,
            height = 20,
            fruit = Fruit(10, 10),
            snake = Snake(10, 10, 0, -1),
            ticks = listOf(
                Movement(velX=0, velY=1),
                Movement(velX=-1, velY=0),
                Movement(velX=0, velY=-1),
            )
        )

        val response = client.post("/validate") {
            contentType(ContentType.Application.Json)
            setBody(endGameState)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(endGameState.gameId, response.bodyAsText().split("gameId")[1].split(":\"")[1].split("\"")[0])
        assertEquals(endGameState.score+1, response.bodyAsText().split("score")[1].split(":")[1].split(",")[0].toInt())
    }

    @Test
    fun `405`() = testApplication {

        application {
            configureRouting()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val endGameState = anyEndGameState().copy(
            width = 20,
            height = 20,
            fruit = Fruit(10, 10),
            snake = Snake(10, 10, 1, 0),
            ticks = listOf(
                Movement(velX=0, velY=1),
                Movement(velX=-1, velY=0),
                Movement(velX=1, velY=0),
            )
        )

        val response = client.post("/validate") {
            contentType(ContentType.Application.Json)
            setBody(endGameState)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(endGameState.gameId, response.bodyAsText().split("gameId")[1].split(":\"")[1].split("\"")[0])
        assertEquals(endGameState.score+1, response.bodyAsText().split("score")[1].split(":")[1].split(",")[0].toInt())
    }
}
