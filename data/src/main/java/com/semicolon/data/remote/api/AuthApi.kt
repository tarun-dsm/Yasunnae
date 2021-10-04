package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.LoginRequest
import com.semicolon.data.remote.response.AccessTokenResponse
import com.semicolon.data.remote.response.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    @POST("auth")
    fun login(
        @Body loginRequest: LoginRequest
    ): Single<TokenResponse>

    @PATCH("auth")
    fun tokenRefresh(
        @Header("X-Refresh-Token") token: String
    ): Single<TokenResponse>
}