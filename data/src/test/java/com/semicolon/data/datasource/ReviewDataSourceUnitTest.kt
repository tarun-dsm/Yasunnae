package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ReviewApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.domain.param.ReviewParam
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ReviewDataSourceUnitTest {

    @Mock
    private lateinit var reviewApi: ReviewApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var reviewDataSource: ReviewDataSource

    private val token = "abc.def.ghi"

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        reviewDataSource = ReviewDataSourceImpl(tokenStorage, reviewApi)

        `when`(tokenStorage.getAccessToken())
            .thenReturn(token)
    }

    @Test
    fun writeReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )

        `when`(
            reviewApi.writeReview(
                token, reviewParam.id, reviewParam.toRequestParam()
            )
        ).thenReturn(Completable.complete())

        reviewDataSource.writeReview(reviewParam).test()
            .assertComplete()
    }

    @Test
    fun writeReviewErrorTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )
        val exception = Exception()

        `when`(
            reviewApi.writeReview(
                token, reviewParam.id, reviewParam.toRequestParam()
            )
        ).thenReturn(Completable.error(exception))

        reviewDataSource.writeReview(reviewParam).test()
            .assertError(exception)
    }

    @Test
    fun deleteReviewSuccessTest() {
        val id = 1234

        `when`(reviewApi.deleteReview(token, id))
            .thenReturn(Completable.complete())

        reviewDataSource.deleteReview(id).test()
            .assertComplete()
    }

    @Test
    fun deleteReviewErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(reviewApi.deleteReview(token, id))
            .thenReturn(Completable.error(exception))

        reviewDataSource.deleteReview(id).test()
            .assertError(exception)
    }

    @Test
    fun editReviewSuccessTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )

        `when`(
            reviewApi.editReview(
                token, reviewParam.id, reviewParam.toRequestParam()
            )
        ).thenReturn(Completable.complete())

        reviewDataSource.editReview(reviewParam).test()
            .assertComplete()
    }

    @Test
    fun editReviewErrorTest() {
        val reviewParam = ReviewParam(
            1234, 5.0, "good"
        )
        val exception = Exception()

        `when`(
            reviewApi.editReview(
                token, reviewParam.id, reviewParam.toRequestParam()
            )
        ).thenReturn(Completable.error(exception))

        reviewDataSource.editReview(reviewParam).test()
            .assertError(exception)
    }
}