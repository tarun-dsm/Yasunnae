package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toResource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.repository.EmailRepository
import com.semicolon.domain.repository.PostRepository
import io.reactivex.Single
import java.io.File

class PostServiceImpl(
    private val postRepository: PostRepository,
    private val errorHandler: ErrorHandler
) : PostService {

    override fun sendPostImage(postImageParam: PostImageParam): Single<Resource<Unit>> =
        postRepository.sendPostImage(postImageParam).toSingleResource(errorHandler)

    override fun writePost(postParam: PostParam): Single<Resource<Int>> =
        postRepository.writePost(postParam).toResource(errorHandler)

    override fun getPostList(): Single<Resource<List<PostEntity>>> =
        postRepository.getPostList().toResource(errorHandler)

    override fun fixPost(fixedPostParam: FixedPostParam): Single<Resource<Int>> =
        postRepository.fixPost(fixedPostParam).toResource(errorHandler)

    override fun deletePost(id: Int): Single<Resource<Unit>> =
        postRepository.deletePost(id).toSingleResource(errorHandler)

    override fun getPostDetail(id: Int): Single<Resource<PostDetailEntity>> =
        postRepository.getPostDetail(id).toResource(errorHandler)

    override fun getPostApplication(id: Int): Single<Resource<List<PostApplicationEntity>>> =
        postRepository.getPostApplication(id).toResource(errorHandler)
}