package com.semicolon.domain.usecase.comment

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.domain.service.CommentService
import io.reactivex.Single

class GetCommentListUseCase(
    private val commentService: CommentService
) : UseCase<Int, Resource<List<CommentEntity>>>() {

    override fun interact(data: Int?): Single<Resource<List<CommentEntity>>> =
        commentService.getCommentList(data!!)
}