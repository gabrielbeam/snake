package com.example.model

import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.random.Random

@Serializable
data class GameState(
    override val gameId: String,
    override val width: Int,
    override val height: Int,
    override val score: Int,
    override val fruit: Fruit,
    override val snake: Snake,
): State {
    init {
        validate()
    }

    companion object {
        fun create(
            gameId: String = UUID.randomUUID().toString().substring(0, 10),
            width: Int = 10,
            height: Int = 10,
            score: Int = 0,
            fruit: Fruit = Fruit(
                x = if (width <= 0) 1 else Random.nextInt(0, width),
                y = if (height <= 0) 1 else Random.nextInt(1, height)
            ),
            snake: Snake = Snake(0, 0, 1, 0)
        ) : GameState = GameState(
            gameId = gameId,
            width = width,
            height = height,
            score = score,
            fruit = fruit,
            snake = snake,
        )
    }
}
