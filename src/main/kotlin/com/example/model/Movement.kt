package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Movement (
   val velX: Int,
   val velY: Int,
) {
    init {
        if (
            velX == velY ||
            velX == -velY ||
            velX < -1 ||
            velY < -1 ||
            velX > 1 ||
            velY > 1) {
            throw InvalidMovementException("Invalid movement.")
        }
    }

    fun isOppositeDirection(movement: Movement): Boolean {
        return (velX == -movement.velX && velY == movement.velY) || (velX == movement.velX && velY == -movement.velY)
    }
}

class InvalidMovementException(message: String) : Exception(message)
