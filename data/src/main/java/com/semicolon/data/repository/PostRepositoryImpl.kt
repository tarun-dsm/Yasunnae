package com.semicolon.data.repository

import com.semicolon.data.datasource.PostDataSource
import com.semicolon.data.remote.response.toEntity
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.repository.PostRepository
import io.reactivex.Completable
import io.reactivex.Single

class PostRepositoryImpl(
    private val postDataSource: PostDataSource
) : PostRepository {

    override fun sendPostImage(postImageParam: PostImageParam): Completable =
        postDataSource.sendPostImage(postImageParam)

    override fun writePost(postParam: PostParam): Single<Int> =
        postDataSource.writePost(postParam).map { it.postId }

    override fun getPostList(): Single<List<PostEntity>> =
        postDataSource.getPostList()
            .map { response -> response.posts.map { it.toEntity() } }

    override fun fixPost(fixedPostParam: FixedPostParam): Single<Int> =
        postDataSource.fixPost(fixedPostParam).map { it.postId }

    override fun deletePost(id: Int): Completable =
        postDataSource.deletePost(id)

    override fun getPostDetail(id: Int): Single<PostDetailEntity> =
        postDataSource.getPostDetail(id).map { it.toEntity() }

    override fun getPostApplication(id: Int): Single<List<PostApplicationEntity>> =
        postDataSource.getPostApplication(id)
            .map { response -> response.applications.map { it.toEntity() } }
}