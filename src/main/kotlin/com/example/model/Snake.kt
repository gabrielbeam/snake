package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Snake(
    val x: Int,
    val y: Int,
    val velX: Int,
    val velY: Int,
    )
