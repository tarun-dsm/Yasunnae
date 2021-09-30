package com.semicolon.data.repository

import com.semicolon.data.datasource.ProfileDataSource
import com.semicolon.data.remote.response.toEntity
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.entity.ReviewEntity
import com.semicolon.domain.repository.ProfileRepository
import io.reactivex.Single

class ProfileRepositoryImpl(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {

    override fun getProfile(id: Int?): Single<ProfileEntity> =
        profileDataSource.getProfile(id).map { it.toEntity() }

    override fun getReview(id: Int?): Single<List<ReviewEntity>> =
        profileDataSource.getReview(id)
            .map { response -> response.reviews.map { it.toEntity() } }

    override fun getProfilePost(id: Int?): Single<List<ProfilePostEntity>> =
        profileDataSource.getProfilePost(id)
            .map { response -> response.posts.map { it.toEntity() } }
}