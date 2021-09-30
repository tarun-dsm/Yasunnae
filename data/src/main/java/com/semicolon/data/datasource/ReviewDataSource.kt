package com.semicolon.data.datasource

import com.semicolon.domain.param.ReviewParam
import io.reactivex.Completable

interface ReviewDataSource {

    fun writeReview(reviewParam: ReviewParam): Completable

    fun deleteReview(id: Int): Completable

    fun editReview(reviewParam: ReviewParam): Completable
}