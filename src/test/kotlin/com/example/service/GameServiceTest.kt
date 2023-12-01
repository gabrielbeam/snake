package com.example.service

import com.example.model.Fruit
import com.example.model.Movement
import com.example.model.Snake
import com.example.model.anyEndGameState
import kotlin.test.*

class GameServiceTest {


    @Test
    fun testCreateState() {
        // given
        val gameService = GameService()

        // when
        val result = gameService.createState(width = 10, height = 10)

        // then
        assertEquals(10, result.width)
        assertEquals(10, result.height)
    }


    @Test
    fun `returns true because snake get the fruit at its last move`() {
        // given
        val gameService = GameService()
        val endGameState = anyEndGameState().copy(
            width = 20,
            height = 20,
            fruit = Fruit(x = 10, y = 10),
            snake = Snake(x = 10, y = 10, velX = 0, velY = 1),
            ticks = listOf(
                Movement(velX=0, velY=1),
            )
        )

        // when
        val result = gameService.validate(endGameState)

        // then
        assertEquals(true, result)
    }

    @Test
    fun `returns false because snake went out of bound`() {
        // given
        val gameService = GameService()
        val endGameState = anyEndGameState().copy(
            width = 20,
            height = 20,
            fruit = Fruit(x = 0, y = 1),
            snake = Snake(x = 20, y = 19, velX = 0, velY = -1),
            ticks = listOf(
                Movement(velX=0, velY=1),
                Movement(velX=1, velY=0),
            )
        )

        // when
        val result = gameService.validate(endGameState)

        // then
        assertEquals(false, result)
    }

    @Test
    fun `returns false because the snake did a U turn`() {
        // given
        val gameService = GameService()
        val endGameState = anyEndGameState().copy(
            width = 20,
            height = 20,
            fruit = Fruit(x = 0, y = 1),
            snake = Snake(x = 19, y = 3, velX = 0, velY = -1),
            ticks = listOf(
                Movement(velX=0, velY=1),
                Movement(velX=0, velY=-1),
            )
        )

        // when
        val result = gameService.validate(endGameState)

        // then
        assertEquals(false, result)
    }
}