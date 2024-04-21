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
    val is_bump: Boolean,
    val object_bump_info: User?
)

data class AccountObject(
    val id: Int,
    var tags: List<Tag>,
    val about: String
)
