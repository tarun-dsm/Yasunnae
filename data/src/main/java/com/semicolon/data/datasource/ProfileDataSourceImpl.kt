package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ProfileApi
import com.semicolon.data.remote.response.ProfilePostResponse
import com.semicolon.data.remote.response.ProfileResponse
import com.semicolon.data.remote.response.ProfileReviewResponse
import io.reactivex.Single

class ProfileDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val profileApi: ProfileApi
) : ProfileDataSource {

    override fun getProfile(id: Int?): Single<ProfileResponse> =
        if (id != null) profileApi.getProfile(
            tokenStorage.getAccessToken(), id
        ) else profileApi.getMyProfile(tokenStorage.getAccessToken())

    override fun getReview(id: Int?): Single<ProfileReviewResponse> =
        if (id != null) profileApi.getProfileReview(
            tokenStorage.getAccessToken(), id
        ) else profileApi.getMyProfileReview(tokenStorage.getAccessToken())

    override fun getProfilePost(id: Int?): Single<ProfilePostResponse> =
        if (id != null) profileApi.getProfilePost(
            tokenStorage.getAccessToken(), id
        ) else profileApi.getMyProfilePost(tokenStorage.getAccessToken())
}