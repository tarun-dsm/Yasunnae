package com.semicolon.data.repository

import com.semicolon.data.datasource.CommentDataSource
import com.semicolon.data.remote.response.toEntity
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.param.CommentParam
import com.semicolon.domain.repository.CommentRepository
import io.reactivex.Completable
import io.reactivex.Single

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource
) : CommentRepository {

    override fun addComment(commentParam: CommentParam): Completable =
        commentDataSource.addComment(commentParam)

    override fun fixComment(commentParam: CommentParam): Completable =
        commentDataSource.fixComment(commentParam)

    override fun deleteComment(id: Int): Completable =
        commentDataSource.deleteComment(id)

    override fun getCommentList(id: Int): Single<List<CommentEntity>> =
        commentDataSource.getCommentList(id)
            .map { it.comments.map { comment -> comment.toEntity() } }
}