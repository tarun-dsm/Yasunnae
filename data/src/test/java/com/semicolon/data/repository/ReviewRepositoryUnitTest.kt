package com.semicolon.data.repository

import com.semicolon.data.datasource.ReviewDataSource
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.repository.ReviewRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ReviewRepositoryUnitTest {

    @Mock
    private lateinit var reviewDataSource: ReviewDataSource

    private lateinit var reviewRepository: ReviewRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        reviewRepository = ReviewRepositoryImpl(reviewDataSource)
    }

    @Test
    fun writeReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )

        `when`(reviewDataSource.writeReview(reviewParam))
            .thenReturn(Completable.complete())

        reviewRepository.writeReview(reviewParam).test()
            .assertComplete()
    }

    @Test
    fun writeReviewErrorTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )
        val exception = Exception()

        `when`(reviewDataSource.writeReview(reviewParam))
            .thenReturn(Completable.error(exception))

        reviewRepository.writeReview(reviewParam).test()
            .assertError(exception)
    }

    @Test
    fun deleteReviewSuccessTest() {
        val id = 1234

        `when`(reviewDataSource.deleteReview(id))
            .thenReturn(Completable.complete())

        reviewRepository.deleteReview(id).test()
            .assertComplete()
    }

    @Test
    fun deleteReviewErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(reviewDataSource.deleteReview(id))
            .thenReturn(Completable.error(exception))

        reviewRepository.deleteReview(id).test()
            .assertError(exception)
    }

    @Test
    fun editReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )

        `when`(reviewDataSource.editReview(reviewParam))
            .thenReturn(Completable.complete())

        reviewRepository.editReview(reviewParam).test()
            .assertComplete()
    }

    @Test
    fun editReviewErrorTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )
        val exception = Exception()

        `when`(reviewDataSource.editReview(reviewParam))
            .thenReturn(Completable.error(exception))

        reviewRepository.editReview(reviewParam).test()
            .assertError(exception)
    }
}