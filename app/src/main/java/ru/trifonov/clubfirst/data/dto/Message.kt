package ru.trifonov.clubfirst.data.dto

data class Messages(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Message>
)

data class Message(
    val id: Int,
    val text: String,
    val author: User
)