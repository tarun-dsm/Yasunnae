package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class GetPostDetailUseCase(
    private val postService: PostService
) : UseCase<Int, Resource<PostDetailEntity>>() {

    override fun interact(data: Int?): Single<Resource<PostDetailEntity>> =
        postService.getPostDetail(data!!)
}