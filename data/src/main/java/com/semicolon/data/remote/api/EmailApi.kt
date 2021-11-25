package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.EmailRequest
import com.semicolon.data.remote.request.SendCertificationEmailRequest
import io.reactivex.Completable
import retrofit2.http.*

interface EmailApi {

    @PATCH("email")
    fun checkCertificationNumber(
        @Body emailRequest: EmailRequest
    ): Completable

    @POST("email")
    fun sendCertificationEmail(
        @Body sendCertificationEmailRequest: SendCertificationEmailRequest
    ): Completable
}