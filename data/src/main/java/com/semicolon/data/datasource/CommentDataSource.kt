package com.semicolon.data.datasource

import com.semicolon.data.remote.response.CommentResponse
import com.semicolon.domain.param.CommentParam
import io.reactivex.Completable
import io.reactivex.Single

interface CommentDataSource {

    fun addComment(commentParam: CommentParam): Completable

    fun fixComment(commentParam: CommentParam): Completable

    fun deleteComment(id: Int): Completable

    fun getCommentList(id: Int): Single<CommentResponse>
}