package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.entity.ReviewEntity
import io.reactivex.Single

interface ProfileService {

    fun getProfile(id: Int?): Single<Resource<ProfileEntity>>

    fun getReview(id: Int?): Single<Resource<List<ReviewEntity>>>

    fun getProfilePost(id: Int?): Single<Resource<List<ProfilePostEntity>>>
}