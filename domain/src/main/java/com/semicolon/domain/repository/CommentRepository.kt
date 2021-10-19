package com.semicolon.domain.repository

import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.param.CommentParam
import io.reactivex.Completable
import io.reactivex.Single

interface CommentRepository {

    fun addComment(commentParam: CommentParam): Completable

    fun fixComment(commentParam: CommentParam): Completable

    fun deleteComment(id: Int): Completable

    fun getCommentList(id: Int): Single<List<CommentEntity>>
}