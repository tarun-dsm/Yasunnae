package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class DeletePostUseCase(
    private val postService: PostService
) : UseCase<Int, Resource<Unit>>() {

    override fun interact(data: Int?): Single<Resource<Unit>> =
        postService.deletePost(data!!)
}