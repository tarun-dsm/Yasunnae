package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.CoordinateRequest
import com.semicolon.data.remote.request.RegisterAccountRequest
import com.semicolon.data.remote.request.ReportRequest
import com.semicolon.data.remote.response.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface AccountApi {

    @POST("account")
    fun registerAccount(
        @Body registerAccountRequest: RegisterAccountRequest
    ): Single<TokenResponse>

    @GET("account/email/{email}")
    fun checkEmailDuplication(
        @Path("email") email: String
    ): Completable

    @GET("account/nickname/{nickname}")
    fun checkNicknameDuplication(
        @Path("nickname") nickname: String
    ): Completable

    @PATCH("account")
    fun saveCoordinate(
        @Header("Authorization") token: String,
        @Body coordinateRequest: CoordinateRequest
    ): Completable

    @POST("account/report/{id}")
    fun reportUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reportRequest: ReportRequest
    ): Completable
}