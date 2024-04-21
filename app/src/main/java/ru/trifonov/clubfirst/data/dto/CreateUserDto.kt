package ru.trifonov.clubfirst.data.dto

data class CreateUserDto(
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    val time_preference: String,
    val tags: List<Int>,
    val position: Int,
    val about: String
)