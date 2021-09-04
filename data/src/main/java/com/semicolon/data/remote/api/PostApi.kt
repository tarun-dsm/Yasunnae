package com.semicolon.data.remote.api

import com.semicolon.data.remote.request.PostRequest
import com.semicolon.data.remote.response.PostApplicationResponse
import com.semicolon.data.remote.response.PostDetailResponse
import com.semicolon.data.remote.response.PostIdResponse
import com.semicolon.data.remote.response.PostListResponse
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {

    @Multipart
    @POST("post/{id}")
    fun sendPostImage(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part images: List<MultipartBody.Part>
    ): Completable

    @POST("post")
    fun writePost(
        @Header("Authorization") token: String,
        @Body post: PostRequest
    ): Single<PostIdResponse>

    @GET("post")
    fun getPostList(
        @Header("Authorization") token: String,
    ): Single<PostListResponse>

    @PATCH("post/{id}")
    fun fixPost(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body post: PostRequest
    ): Single<PostIdResponse>

    @DELETE("post/{id}")
    fun deletePost(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Completable

    @GET("post/details/{id}")
    fun getPostDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Single<PostDetailResponse>

    @GET("application/post/{id}")
    fun getPostApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Single<PostApplicationResponse>
}