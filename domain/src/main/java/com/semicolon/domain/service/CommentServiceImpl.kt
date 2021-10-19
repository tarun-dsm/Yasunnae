package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toResource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.param.CommentParam
import com.semicolon.domain.repository.CommentRepository
import io.reactivex.Single

class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val errorHandler: ErrorHandler
) : CommentService {

    override fun addComment(commentParam: CommentParam): Single<Resource<Unit>> =
        commentRepository.addComment(commentParam).toSingleResource(errorHandler)

    override fun fixComment(commentParam: CommentParam): Single<Resource<Unit>> =
        commentRepository.fixComment(commentParam).toSingleResource(errorHandler)

    override fun deleteComment(id: Int): Single<Resource<Unit>> =
        commentRepository.deleteComment(id).toSingleResource(errorHandler)

    override fun getCommentList(id: Int): Single<Resource<List<CommentEntity>>> =
        commentRepository.getCommentList(id).toResource(errorHandler)
}