package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.repository.ReviewRepository
import io.reactivex.Single

class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val errorHandler: ErrorHandler
) : ReviewService {

    override fun writeReview(reviewParam: ReviewParam): Single<Resource<Unit>> =
        reviewRepository.writeReview(reviewParam).toSingleResource(errorHandler)

    override fun deleteReview(id: Int): Single<Resource<Unit>> =
        reviewRepository.deleteReview(id).toSingleResource(errorHandler)

    override fun editReview(reviewParam: ReviewParam): Single<Resource<Unit>> =
        reviewRepository.editReview(reviewParam).toSingleResource(errorHandler)
}