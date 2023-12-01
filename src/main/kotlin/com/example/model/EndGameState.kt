package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class EndGameState (
    override val gameId: String,
    override val width: Int,
    override val height: Int,
    override val score: Int,
    override val fruit: Fruit,
    override val snake: Snake,
    val ticks: List<Movement>,
): State {
    init {
        validate()
    }
}
