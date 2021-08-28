package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class WritePostUseCase(
    private val postService: PostService
) : UseCase<PostParam, Resource<Int>>() {

    override fun interact(data: PostParam): Single<Resource<Int>> =
        postService.writePost(data)
}