package ru.trifonov.clubfirst.data.dto

data class LoginResult(
    val user: User,
    val access: String,
    val refresh: String
)
