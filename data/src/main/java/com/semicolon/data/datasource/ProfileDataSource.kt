package com.semicolon.data.datasource

import com.semicolon.data.remote.response.ProfilePostResponse
import com.semicolon.data.remote.response.ProfileResponse
import com.semicolon.data.remote.response.ProfileReviewResponse
import io.reactivex.Single

interface ProfileDataSource {

    fun getProfile(id: Int?): Single<ProfileResponse>

    fun getReview(id: Int?): Single<ProfileReviewResponse>

    fun getProfilePost(id: Int?): Single<ProfilePostResponse>
}