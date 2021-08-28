package com.semicolon.domain.repository

import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.entity.ReviewEntity
import io.reactivex.Single

/*
    If parameter 'id' is null,
    the functions return user-owned information.
 */
interface ProfileRepository {

    fun getProfile(id: Int?): Single<ProfileEntity>

    fun getReview(id: Int?): Single<List<ReviewEntity>>

    fun getProfilePost(id: Int?): Single<List<ProfilePostEntity>>
}