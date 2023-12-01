package com.example.plugins

import com.example.model.*
import com.example.service.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.lang.NumberFormatException

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    val logger = LoggerFactory.getLogger(this.javaClass.name)
    routing {
        get("/new") {
            try {
                val width = call.request.queryParameters["w"]
                val height = call.request.queryParameters["h"]
                if (!width.isNullOrEmpty() && !height.isNullOrEmpty()) {
                    val state = GameService().createState(width.toInt(), height.toInt())
                    call.respond(HttpStatusCode.OK, state)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Missing width or height.")
                }
            } catch (e: InvalidMapException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            } catch (e: NumberFormatException) {
                call.respond(HttpStatusCode.BadRequest, "Width and height should be integer.")
            } catch (e: Exception) {
                logger.error("Error creating new game: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, e.message.toString())
            }
        }

        post("/validate") {
            try {
                val endGameState = call.receive<EndGameState>()
                if (GameService().validate(endGameState)) {
                    val newGame = GameState.create(
                        gameId = endGameState.gameId,
                        width = endGameState.width,
                        height = endGameState.height,
                        score = endGameState.score+1,
                    )
                    call.respond(HttpStatusCode.OK, newGame)
                } else {
                    call.respond(
                        HttpStatusCode(418, "I'm a tea pot"),
                        "Snake went out of bounds or made an invalid move."
                    )
                }
                call.respond(HttpStatusCode.OK, endGameState)
            } catch(e: FruitNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: InvalidMovementException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            } catch (e: Exception) {
                logger.error("Error validating game: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, e.message.toString())
            }
        }
    }
}
