package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.ReviewParam
import io.reactivex.Single

interface ReviewService {

    fun writeReview(reviewParam: ReviewParam): Single<Resource<Unit>>

    fun deleteReview(id: Int): Single<Resource<Unit>>

    fun editReview(reviewParam: ReviewParam): Single<Resource<Unit>>
}