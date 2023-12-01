package com.example.model

import kotlin.test.Test
import kotlin.test.*

class GameStateTest {
    @Test
    fun `create GameState by using its builder function`() {
        //given
        val width = 100
        val height = 100
        val score = 0

        //when
        val gameState = GameState.create(width = width, height = height)

        //then
        assertEquals(width, gameState.width)
        assertEquals(height, gameState.height)
        assertEquals(10, gameState.gameId.length)
        assertEquals(score, gameState.score)
        assertTrue(gameState.fruit.x >= 0)
        assertTrue(gameState.fruit.x < width)
        assertTrue(gameState.fruit.y >= 1)
        assertTrue(gameState.fruit.y < height)
        assertEquals(0, gameState.snake.x)
        assertEquals(0, gameState.snake.y)
        assertEquals(1, gameState.snake.velX)
        assertEquals(0, gameState.snake.velY)
    }

    @Test
    fun `throw InvalidMapException when width is a negative number`() {
        //given
        val width = -100
        val height = 100

        //when
        val exception = assertFailsWith<InvalidMapException> {
            GameState.create(width = width, height = height)
        }

        //then
        assertEquals("The width and height of the map must be greater than 0.", exception.message)
    }

    @Test
    fun `throw FruitNotFoundException when the fruit is out of the map`() {
        //given
        val width = 100
        val height = 100

        //when
        val exception = assertFailsWith<FruitNotFoundException> {
            GameState.create(width = width, height = height, fruit = Fruit(x = 101, y = 0))
        }

        //then
        assertEquals("Fruit not found, the ticks do not lead the snake to the fruit position.", exception.message)
    }
}
