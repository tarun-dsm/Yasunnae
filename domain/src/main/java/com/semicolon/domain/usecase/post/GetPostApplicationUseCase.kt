package com.semicolon.domain.usecase.post

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.service.PostService
import io.reactivex.Single

class GetPostApplicationUseCase(
    private val postService: PostService
) : UseCase<Int, Resource<List<PostApplicationEntity>>>() {

    override fun interact(data: Int?): Single<Resource<List<PostApplicationEntity>>> =
        postService.getPostApplication(data!!)
}