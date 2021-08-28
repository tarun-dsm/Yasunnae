package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class GetPostListUseCase(
    private val postService: PostService
) : UseCase<Unit, Resource<List<PostEntity>>>() {

    override fun interact(data: Unit): Single<Resource<List<PostEntity>>> =
        postService.getPostList()
}