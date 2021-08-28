package com.semicolon.domain.usecase.review

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.service.ReviewService
import io.reactivex.Single

class EditReviewUseCase(
    private val reviewService: ReviewService
) : UseCase<ReviewParam, Resource<Unit>>() {

    override fun interact(data: ReviewParam?): Single<Resource<Unit>> =
        reviewService.editReview(data!!)
}