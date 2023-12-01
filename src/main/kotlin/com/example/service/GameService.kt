package com.example.service

import com.example.model.*

class GameService {
    fun createState(width:Int, height: Int): GameState {
        return GameState.create(width = width, height = height)
    }

    fun validate(endGameState: EndGameState): Boolean {
        if (endGameState.snake.x < 0 ||
            endGameState.snake.x >= endGameState.width ||
            endGameState.snake.y < 0 ||
            endGameState.snake.y >= endGameState.height ||
            endGameState.snake.x != endGameState.fruit.x ||
            endGameState.snake.y != endGameState.fruit.y ||
            endGameState.snake.velX != endGameState.ticks.last().velX ||
            endGameState.snake.velY != endGameState.ticks.last().velY
            ) {
            return false
        }
        for (i in 1 until endGameState.width) {
            if (dfs(
                    x = i,
                    y = 0,
                    previousMovement = Movement(1, 0),
                    ticks = endGameState.ticks,
                    fruit = endGameState.fruit,
                    width = endGameState.width,
                    height = endGameState.height,
                    index = 0,
                )) {
                return true
            }
        }
        return false
    }

    private fun dfs(x: Int, y: Int, previousMovement: Movement, ticks: List<Movement>, fruit: Fruit, width: Int, height: Int, index: Int): Boolean {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false
        }
        if (x == fruit.x && y == fruit.y) {
            return index == ticks.size
        } else if (index >= ticks.size || previousMovement.isOppositeDirection(ticks[index])) {
            return false
        }
        val movement = ticks[index]
        if (movement.velX > 0 && movement.velY == 0) { //Moving right
            for (i in 1 until width-x) {
                if (dfs(x+i, y, ticks[index], ticks, fruit, width, height, index + 1)) {
                    return true
                }
            }
        } else if (movement.velX == 0 && movement.velY > 0) { //Moving down
            for (j in 1 until height-y) {
                if (dfs(x, y+j, ticks[index], ticks, fruit, width, height, index + 1)) {
                    return true
                }
            }
        } else if (movement.velX < 0 && movement.velY == 0) { //Moving left
            for (i in x downTo  1) {
                if (dfs(x-i, y, ticks[index], ticks, fruit, width, height, index + 1)) {
                    return true
                }
            }
        } else if (movement.velX == 0 && movement.velY < 0) { //Moving up
            for (j in y downTo 1) {
                if (dfs(x, y-j, ticks[index], ticks, fruit, width, height, index + 1)) {
                    return true
                }
            }
        }
        return false
    }
}
