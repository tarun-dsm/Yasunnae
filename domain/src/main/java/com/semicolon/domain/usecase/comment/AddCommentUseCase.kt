package com.semicolon.domain.usecase.comment

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.CommentParam
import com.semicolon.domain.service.CommentService
import io.reactivex.Single

class AddCommentUseCase(
    private val commentService: CommentService
) : UseCase<CommentParam, Resource<Unit>>() {

    override fun interact(data: CommentParam?): Single<Resource<Unit>> =
        commentService.addComment(data!!)
}