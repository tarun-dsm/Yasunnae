package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ReviewApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.domain.param.ReviewParam
import io.reactivex.Completable

class ReviewDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val reviewApi: ReviewApi
) : ReviewDataSource {

    override fun writeReview(reviewParam: ReviewParam): Completable =
        reviewApi.writeReview(
            tokenStorage.getAccessToken(),
            reviewParam.id,
            reviewParam.toRequestParam()
        )

    override fun deleteReview(id: Int): Completable =
        reviewApi.deleteReview(tokenStorage.getAccessToken(), id)

    override fun editReview(reviewParam: ReviewParam): Completable =
        reviewApi.editReview(
            tokenStorage.getAccessToken(),
            reviewParam.id,
            reviewParam.toRequestParam()
        )
}