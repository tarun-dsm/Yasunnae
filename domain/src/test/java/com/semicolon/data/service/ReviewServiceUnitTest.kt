package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.repository.ReviewRepository
import com.semicolon.domain.service.ReviewService
import com.semicolon.domain.service.ReviewServiceImpl
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ReviewServiceUnitTest {

    @Mock
    private lateinit var reviewRepository: ReviewRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var reviewService: ReviewService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        reviewService = ReviewServiceImpl(reviewRepository, errorHandler)
    }

    @Test
    fun writeReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewRepository.writeReview(reviewParam))
            .thenReturn(Completable.complete())

        reviewService.writeReview(reviewParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun writeReviewErrorTest() {
        val exception = Exception()
        val errorMessage = Error.FORBIDDEN
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewRepository.writeReview(reviewParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        reviewService.writeReview(reviewParam).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun deleteReviewSuccessTest() {
        val id = 1234

        `when`(reviewRepository.deleteReview(id))
            .thenReturn(Completable.complete())

        reviewService.deleteReview(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun deleteReviewErrorTest() {
        val exception = Exception()
        val errorMessage = Error.FORBIDDEN
        val id = 1234

        `when`(reviewRepository.deleteReview(id))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        reviewService.deleteReview(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun editReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewRepository.editReview(reviewParam))
            .thenReturn(Completable.complete())

        reviewService.editReview(reviewParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun editReviewErrorTest() {
        val exception = Exception()
        val errorMessage = Error.FORBIDDEN
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewRepository.editReview(reviewParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        reviewService.editReview(reviewParam).test()
            .assertValue(Resource.error(errorMessage))
    }
}