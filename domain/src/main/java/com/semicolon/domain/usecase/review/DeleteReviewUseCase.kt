package com.semicolon.domain.usecase.review

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.ReviewService
import io.reactivex.Single

class DeleteReviewUseCase(
    private val reviewService: ReviewService
) : UseCase<Int, Resource<Unit>>() {

    override fun interact(data: Int?): Single<Resource<Unit>> =
        reviewService.deleteReview(data!!)
}