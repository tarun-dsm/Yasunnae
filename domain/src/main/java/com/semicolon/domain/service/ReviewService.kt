package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import io.reactivex.Single

interface ReviewService {

    fun writeReview(id: Int, grade: Double, comment: String): Single<Resource<Unit>>

    fun deleteReview(id: Int): Single<Resource<Unit>>

    fun editReview(id: Int, grade: Double, comment: String): Single<Resource<Unit>>
}