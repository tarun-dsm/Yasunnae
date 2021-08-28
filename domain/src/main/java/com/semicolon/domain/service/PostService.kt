package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.param.PostParam
import io.reactivex.Single
import java.io.File

interface PostService {

    fun sendPostImage(id: Int, image: File): Single<Resource<Unit>>

    fun writePost(postParam: PostParam): Single<Resource<Int>>

    fun getPostList(): Single<Resource<List<PostEntity>>>

    fun fixPost(id: Int, postParam: PostParam): Single<Resource<Int>>

    fun deletePost(id: Int): Single<Resource<Unit>>

    fun getPostDetail(id: Int): Single<Resource<PostDetailEntity>>

    fun getPostApplication(id: Int): Single<Resource<List<PostApplicationEntity>>>
}