package com.semicolon.domain.usecase.profile

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.ReviewEntity
import com.semicolon.domain.service.ProfileService
import io.reactivex.Single

class GetReviewUseCase(
    private val profileService: ProfileService
) : UseCase<Int, Resource<List<ReviewEntity>>>() {

    override fun interact(data: Int?): Single<Resource<List<ReviewEntity>>> =
        profileService.getReview(data)
}