package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class SendPostImageUseCase(
    private val postService: PostService
) : UseCase<PostImageParam, Resource<Unit>>() {

    override fun interact(data: PostImageParam?): Single<Resource<Unit>> =
        postService.sendPostImage(data!!)
}