package ru.trifonov.clubfirst.data.dto

data class AccountResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Account>
)

data class Account(
    val id: Int,
    val name: String,
    val last_message: Message
)
