package ru.trifonov.clubfirst.data.dto

data class Tag(
    val id: Int,
    val name: String
)

data class TagPagination(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Tag>
)