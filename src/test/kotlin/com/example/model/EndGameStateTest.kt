package com.example.model

import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EndGameStateTest {
    @Test
    fun createEndGameStateTest() {
        //given
        val width = 30
        val height = 30
        val score = Random.nextInt(0, 10)
        val fruit = Fruit(
            x = Random.nextInt(0, width),
            y = Random.nextInt(0, height),
        )

        //then
        val endGameState = anyEndGameState().copy(width = width, height = height, score = score, fruit = fruit)

        assertEquals(10, endGameState.gameId.length)
        assertEquals(width, endGameState.width)
        assertEquals(height, endGameState.height)
        assertEquals(score, endGameState.score)
        assertEquals(fruit, endGameState.fruit)
    }

    @Test
    fun `throw InvalidMethodException when tick is invalid`() {
        //when
        val exception = assertFailsWith<InvalidMovementException> {
            EndGameState(
                gameId = UUID.randomUUID().toString(),
                width = 20,
                height = 20,
                score = Random.nextInt(0, 10),
                fruit = Fruit(
                    x = Random.nextInt(0, 20),
                    y = Random.nextInt(0, 20),
                ),
                snake = Snake(0, 0, 1, 0),
                ticks = listOf(Movement(1,1), Movement(-1,-1), Movement(0,0)),)
        }

        //then
        assertEquals("Invalid movement.", exception.message)
    }
}
