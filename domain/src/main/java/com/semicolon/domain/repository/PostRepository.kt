package com.semicolon.domain.repository

import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface PostRepository {

    fun sendPostImage(postImageParam: PostImageParam): Completable

    fun writePost(postParam: PostParam): Single<Int>

    fun getPostList(): Single<List<PostEntity>>

    fun fixPost(fixedPostParam: FixedPostParam): Single<Int>

    fun deletePost(id: Int): Completable

    fun getPostDetail(id: Int): Single<PostDetailEntity>

    fun getPostApplication(id: Int): Single<List<PostApplicationEntity>>
}