package com.semicolon.data.remote.api

import com.semicolon.data.remote.response.MyApplicationResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ApplicationApi {

    @POST("application/{id}")
    fun sendApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    @DELETE("application/{id}")
    fun cancelApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    @PATCH("application/{id}")
    fun acceptApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    @GET("application")
    fun getMyApplication(
        @Header("Authorization") token: String
    ): Single<MyApplicationResponse>
}