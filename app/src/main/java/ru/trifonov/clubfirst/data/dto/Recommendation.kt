package ru.trifonov.clubfirst.data.dto

data class AccountResponseRec(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<AccountRec>
)

data class AccountRec(
    val id: Int,
    val `object`: AccountObject,
    val state: Int,
    val is_bump: Boolean
)

data class AccountObject(
    val id: Int,
    val tags: List<Tag>,
    val about: String
)
