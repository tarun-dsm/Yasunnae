package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.ReviewParam
import com.semicolon.domain.service.ReviewService
import com.semicolon.domain.usecase.review.DeleteReviewUseCase
import com.semicolon.domain.usecase.review.EditReviewUseCase
import com.semicolon.domain.usecase.review.WriteReviewUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ReviewUseCasesUnitTest {

    @Mock
    private lateinit var reviewService: ReviewService

    private lateinit var deleteReviewUseCase: DeleteReviewUseCase
    private lateinit var editReviewUseCase: EditReviewUseCase
    private lateinit var writeReviewUseCase: WriteReviewUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        deleteReviewUseCase = DeleteReviewUseCase(reviewService)
        editReviewUseCase = EditReviewUseCase(reviewService)
        writeReviewUseCase = WriteReviewUseCase(reviewService)
    }

    @Test
    fun deleteReviewTest() {
        val id = 1234

        `when`(reviewService.deleteReview(id))
            .thenReturn(Single.just(Resource.success(Unit)))

        deleteReviewUseCase.interact(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun editReviewTest() {
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewService.editReview(reviewParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        editReviewUseCase.interact(reviewParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun writeReviewTest() {
        val reviewParam = ReviewParam(
            1234,
            5.0,
            "good"
        )

        `when`(reviewService.writeReview(reviewParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        writeReviewUseCase.interact(reviewParam).test()
            .assertValue(Resource.success(Unit))
    }
}