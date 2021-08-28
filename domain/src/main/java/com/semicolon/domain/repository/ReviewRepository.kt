package com.semicolon.domain.repository

import io.reactivex.Completable

interface ReviewRepository {

    fun writeReview(id: Int, grade: Double, comment: String): Completable

    fun deleteReview(id: Int): Completable

    fun editReview(id: Int, grade: Double, comment: String): Completable
}