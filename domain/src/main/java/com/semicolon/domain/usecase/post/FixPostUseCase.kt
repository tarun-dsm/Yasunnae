package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class FixPostUseCase(
    private val postService: PostService
) : UseCase<FixedPostParam, Resource<Int>>() {

    override fun interact(data: FixedPostParam): Single<Resource<Int>> =
        postService.fixPost(data)
}