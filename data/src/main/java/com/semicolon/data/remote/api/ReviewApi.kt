package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.ReviewRequest
import io.reactivex.Completable
import retrofit2.http.*

interface ReviewApi {

    @POST("review/{id}")
    fun writeReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reviewRequest: ReviewRequest
    ): Completable

    @DELETE("review/{id}")
    fun deleteReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    @PATCH("review/{id}")
    fun editReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reviewRequest: ReviewRequest
    ): Completable
}