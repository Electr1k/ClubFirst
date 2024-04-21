package ru.trifonov.clubfirst.data.dto

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String?,
    val birth_date: String?,
    val ages: Int?,
    val time_preference: String?,
    val groups: List<Group>,
    val tags: List<Tag>,
    val position: Position?,
    val about: String
)