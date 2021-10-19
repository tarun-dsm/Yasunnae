package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.CommentRequest
import com.semicolon.data.remote.response.CommentResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface CommentApi {

    @POST("comment/{id}")
    fun addComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body commentRequest: CommentRequest
    ): Completable

    @PATCH("comment/{id}")
    fun fixComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body commentRequest: CommentRequest
    ): Completable

    @DELETE("comment/{id}")
    fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    @GET("comments")
    fun getCommentList(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Single<CommentResponse>
}