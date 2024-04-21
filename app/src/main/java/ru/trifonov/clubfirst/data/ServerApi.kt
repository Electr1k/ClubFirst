package ru.trifonov.clubfirst.data

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.trifonov.clubfirst.data.dto.AccountRec
import ru.trifonov.clubfirst.data.dto.AccountResponse
import ru.trifonov.clubfirst.data.dto.AccountResponseRec
import ru.trifonov.clubfirst.data.dto.LoginBody
import ru.trifonov.clubfirst.data.dto.LoginResult
import ru.trifonov.clubfirst.data.dto.Messages
import ru.trifonov.clubfirst.data.dto.PositionsPaginated
import ru.trifonov.clubfirst.data.dto.ReactionBody
import ru.trifonov.clubfirst.data.dto.TagPagination
import ru.trifonov.clubfirst.data.dto.User

interface ServerApi {

    @POST("auth/login/")
    suspend fun login(
        @Body email: LoginBody
    ): LoginResult

    @GET("auth/user/")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String,
        ): User

    @Multipart
    @PATCH("auth/user/")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part("first_name") first_name: String,
        @Part("last_name") last_name: String,
        @Part("birth_date") birth_date: String? = null,
        @Part("time_preference") time_preference: String,
        @Part("tags") tags: String? = null,
        @Part("position") position: Int,
        @Part("about") about: String,
        @Part avatar: MultipartBody.Part
    ): Any

    @GET("members/position/")
    suspend fun getPositions(
        @Header("Authorization") token: String
    ): PositionsPaginated

    @GET("recommendations/match/")
    suspend fun getRecommendations(
        @Header("Authorization") token: String
    ): AccountResponseRec

    @GET("chats/chat/")
    suspend fun getChats(
        @Header("Authorization") token: String
    ): AccountResponse

    @GET("chats/chat/{id}/message")
    suspend fun getChatMessage(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Messages


    @GET("members/tag/")
    suspend fun getTags(
        @Header("Authorization") token: String
    ): TagPagination

    @PATCH("recommendations/match/{id}/")
    suspend fun setReactionOnRecommendation(
        @Path("id") id: Int,
        @Body body: ReactionBody,
        @Header("Authorization") token: String
    ): AccountRec


}