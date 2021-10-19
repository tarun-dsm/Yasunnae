package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.param.CommentParam
import io.reactivex.Completable
import io.reactivex.Single

interface CommentService {

    fun addComment(commentParam: CommentParam): Single<Resource<Unit>>

    fun fixComment(commentParam: CommentParam): Single<Resource<Unit>>

    fun deleteComment(id: Int): Single<Resource<Unit>>

    fun getCommentList(id: Int): Single<Resource<List<CommentEntity>>>
}