package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.repository.ReviewRepository
import io.reactivex.Single

class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val errorHandler: ErrorHandler
) : ReviewService {

    override fun writeReview(id: Int, grade: Double, comment: String): Single<Resource<Unit>> =
        reviewRepository.writeReview(id, grade, comment).toSingleResource(errorHandler)

    override fun deleteReview(id: Int): Single<Resource<Unit>> =
        reviewRepository.deleteReview(id).toSingleResource(errorHandler)

    override fun editReview(id: Int, grade: Double, comment: String): Single<Resource<Unit>> =
        reviewRepository.editReview(id, grade, comment).toSingleResource(errorHandler)
}