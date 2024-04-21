package ru.trifonov.clubfirst.data.dto

data class Position(
    val id: Int,
    val name: String
)

data class PositionsPaginated(

    val count: Int,
    val next: Int?,
    val previous: Int?,
    val results: List<Position>
)