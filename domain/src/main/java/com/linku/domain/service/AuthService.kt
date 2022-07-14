package com.linku.domain.service

import com.linku.domain.Auth
import com.linku.domain.Result
import retrofit2.http.*

interface AuthService {
    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("realname") realName: String?
    ): Result<Unit>

    @FormUrlEncoded
    @POST("auth/signin")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Result<Auth.Token>


    @GET("auth/signout")
    suspend fun signOut(): Result<Unit>

    @POST("auth/token")
    suspend fun token(
        @Header("Auth") token: String = Auth.token ?: ""
    ): Result<String>


    @GET("auth/email/{code}")
    suspend fun verifyEmailCode(@Path("code") code: String): Result<Unit>

    @GET("auth/email")
    suspend fun verifyEmail(): Result<Unit>

    @GET("auth/forget")
    suspend fun forgetPassword(code: String): Result<Unit>

    @FormUrlEncoded
    @POST("auth/forget/{code}")
    suspend fun forgetPasswordVerify(@Path("code") code: String): Result<Unit>
}