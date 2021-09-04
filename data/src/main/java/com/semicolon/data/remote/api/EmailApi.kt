package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.SendCertificationEmailRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EmailApi {

    @GET("email")
    fun checkCertificationNumber(
        @Query("email") email: String,
        @Query("number") number: String,
    ): Completable

    @POST("email")
    fun sendCertificationEmail(
        @Body sendCertificationEmailRequest: SendCertificationEmailRequest
    ): Completable
}