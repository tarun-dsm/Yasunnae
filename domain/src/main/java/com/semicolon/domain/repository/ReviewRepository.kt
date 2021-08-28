package com.semicolon.domain.repository

import com.semicolon.domain.param.ReviewParam
import io.reactivex.Completable

interface ReviewRepository {

    fun writeReview(reviewParam: ReviewParam): Completable

    fun deleteReview(id: Int): Completable

    fun editReview(reviewParam: ReviewParam): Completable
}