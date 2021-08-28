package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toResource
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.entity.ReviewEntity
import com.semicolon.domain.repository.ProfileRepository
import io.reactivex.Single

class ProfileServiceImpl(
    private val profileRepository: ProfileRepository,
    private val errorHandler: ErrorHandler
) : ProfileService {

    override fun getProfile(id: Int?): Single<Resource<ProfileEntity>> =
        profileRepository.getProfile(id).toResource(errorHandler)

    override fun getReview(id: Int?): Single<Resource<List<ReviewEntity>>> =
        profileRepository.getReview(id).toResource(errorHandler)

    override fun getProfilePost(id: Int?): Single<Resource<List<ProfilePostEntity>>> =
        profileRepository.getProfilePost(id).toResource(errorHandler)
}