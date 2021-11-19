package com.semicolon.domain.usecase.comment

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.CommentService
import io.reactivex.Single

class DeleteCommentUseCase(
    private val commentService: CommentService
) : UseCase<Int, Resource<Unit>>() {

    override fun interact(data: Int?): Single<Resource<Unit>> =
        commentService.deleteComment(data!!)
}