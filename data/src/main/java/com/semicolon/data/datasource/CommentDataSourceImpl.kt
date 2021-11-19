package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.CommentApi
import com.semicolon.data.remote.request.CommentRequest
import com.semicolon.data.remote.response.CommentResponse
import com.semicolon.domain.param.CommentParam
import io.reactivex.Completable
import io.reactivex.Single

class CommentDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val commentApi: CommentApi
) : CommentDataSource {

    override fun addComment(commentParam: CommentParam): Completable =
        commentApi.addComment(
            tokenStorage.getAccessToken(),
            commentParam.id,
            CommentRequest(commentParam.comment)
        )

    override fun fixComment(commentParam: CommentParam): Completable =
        commentApi.fixComment(
            tokenStorage.getAccessToken(),
            commentParam.id,
            CommentRequest(commentParam.comment)
        )

    override fun deleteComment(id: Int): Completable =
        commentApi.deleteComment(tokenStorage.getAccessToken(), id)

    override fun getCommentList(id: Int): Single<CommentResponse> =
        commentApi.getCommentList(tokenStorage.getAccessToken(), id)
}