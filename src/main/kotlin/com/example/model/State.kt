package com.example.model

interface State {
    val gameId: String
    val width: Int
    val height: Int
    val score: Int
    val fruit: Fruit
    val snake: Snake

    fun validate() {
        if (width <= 0 || height <= 0) {
            throw InvalidMapException("The width and height of the map must be greater than 0.")
        } else if (fruit.x < 0 ||
            fruit.x >= width ||
            fruit.y < 0 ||
            fruit.y >= height) {
            throw FruitNotFoundException("Fruit not found, the ticks do not lead the snake to the fruit position.")
        }
    }
}

class FruitNotFoundException(message: String) : Exception(message)
class InvalidMapException(message: String) : Exception(message)
