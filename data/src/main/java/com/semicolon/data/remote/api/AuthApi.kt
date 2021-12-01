package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.LoginRequest
import com.semicolon.data.remote.response.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface AuthApi {

    @POST("auth")
    fun login(
        @Body loginRequest: LoginRequest
    ): Single<TokenResponse>

    @PUT("auth")
    fun tokenRefresh(
        @Header("X-Refresh-Token") token: String
    ): Single<TokenResponse>

    @DELETE("auth")
    fun logout(
        @Header("Authorization") token: String
    ): Completable
}