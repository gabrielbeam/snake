package com.example.model

import java.util.*
import kotlin.random.Random

internal fun anyEndGameState(): EndGameState {
    val fruit = Fruit(
        x = Random.nextInt(0, 20),
        y = Random.nextInt(0, 20),
    )
    return EndGameState(
        gameId = randomString(),
        width = 20,
        height = 20,
        score = Random.nextInt(0, 10),
        fruit = fruit,
        snake = Snake(fruit.x, fruit.y, 0, -1),
        ticks = listOf(
            Movement(0, 1),
            Movement(1, 0),
            Movement(0, -1),
        )
    )
}


private fun randomString(): String {
    return UUID.randomUUID().toString().substring(0, 10)
}
