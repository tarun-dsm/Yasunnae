package com.semicolon.data.datasource

import com.semicolon.data.remote.request.PostRequest
import com.semicolon.data.remote.response.PostApplicationResponse
import com.semicolon.data.remote.response.PostDetailResponse
import com.semicolon.data.remote.response.PostIdResponse
import com.semicolon.data.remote.response.PostListResponse
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface PostDataSource {

    fun sendPostImage(postImageParam: PostImageParam): Completable

    fun writePost(postParam: PostParam): Single<PostIdResponse>

    fun getPostList(): Single<PostListResponse>

    fun fixPost(fixedPostParam: FixedPostParam): Single<PostIdResponse>

    fun deletePost(id: Int): Completable

    fun getPostDetail(id: Int): Single<PostDetailResponse>

    fun getPostApplication(id: Int): Single<PostApplicationResponse>
}