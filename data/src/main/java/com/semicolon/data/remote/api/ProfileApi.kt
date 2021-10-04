package com.semicolon.data.remote.api

import com.semicolon.data.remote.response.ProfilePostResponse
import com.semicolon.data.remote.response.ProfileResponse
import com.semicolon.data.remote.response.ProfileReviewResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProfileApi {

    @GET("profile/{id}")
    fun getProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ProfileResponse>

    @GET("profile/reviews/{id}")
    fun getProfileReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ProfileReviewResponse>

    @GET("profile/posts/{id}")
    fun getProfilePost(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ProfilePostResponse>

    @GET("profile/")
    fun getMyProfile(
        @Header("Authorization") token: String,
    ): Single<ProfileResponse>

    @GET("profile/reviews")
    fun getMyProfileReview(
        @Header("Authorization") token: String,
    ): Single<ProfileReviewResponse>

    @GET("profile/posts")
    fun getMyProfilePost(
        @Header("Authorization") token: String,
    ): Single<ProfilePostResponse>
}