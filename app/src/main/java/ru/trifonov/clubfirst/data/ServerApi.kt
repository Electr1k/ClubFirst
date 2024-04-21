package ru.trifonov.clubfirst.data

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.trifonov.clubfirst.data.dto.Account
import ru.trifonov.clubfirst.data.dto.AccountResponse
import ru.trifonov.clubfirst.data.dto.AccountResponseRec
import ru.trifonov.clubfirst.data.dto.CreateUserDto
import ru.trifonov.clubfirst.data.dto.PositionsPaginated
import ru.trifonov.clubfirst.data.dto.TagPagination
import ru.trifonov.clubfirst.data.dto.User
import java.io.File

interface ServerApi {

    @POST("auth/login/")
    suspend fun login(
        @Body() email: String
    ): User

    @GET("auth/user/")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo",
        ): User

    @Multipart
    @PATCH("auth/user/")
    suspend fun createUser(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo",
        @Part("first_name") first_name: String,
        @Part("last_name") last_name: String,
        @Part("birth_date") birth_date: String,
        @Part("time_preference") time_preference: String,
        @Part("tags") tags: List<Int>,
        @Part("position") position: Int,
        @Part("about") about: String,
        @Part avatar: MultipartBody.Part
    ): Any

    @GET("members/position/")
    suspend fun getPositions(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo"
    ): PositionsPaginated

    @GET("recommendations/match/")
    suspend fun getRecommendations(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo"
    ): AccountResponseRec

    @GET("chats/chat/")
    suspend fun getChats(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo"
    ): AccountResponse

    @GET("chats/chat/{id}")
    suspend fun getChatById(
        @Query("id") id: Int,
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo"
    ): Account


    @GET("members/tag/")
    suspend fun getTags(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE0MjU3ODYyLCJpYXQiOjE3MTM2NTMwNjIsImp0aSI6IjNmOGM2YjM4MzgzZDQ4MGY5ZWIxNWMxOGVjMjU2ZGFkIiwidXNlcl9pZCI6Mn0.2SVCsnctiJaYgrOhQ_SPqbLtg6UJg_hfcxDjXoBUHCo"
    ): TagPagination
}