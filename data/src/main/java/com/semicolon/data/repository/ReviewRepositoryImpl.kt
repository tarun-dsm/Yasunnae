package com.semicolon.data.repository

import com.semicolon.data.datasource.ReviewDataSource
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.repository.ReviewRepository
import io.reactivex.Completable

class ReviewRepositoryImpl(
    private val reviewDataSource: ReviewDataSource
) : ReviewRepository {

    override fun writeReview(reviewParam: ReviewParam): Completable =
        reviewDataSource.writeReview(reviewParam)

    override fun deleteReview(id: Int): Completable =
        reviewDataSource.deleteReview(id)

    override fun editReview(reviewParam: ReviewParam): Completable =
        reviewDataSource.editReview(reviewParam)
}