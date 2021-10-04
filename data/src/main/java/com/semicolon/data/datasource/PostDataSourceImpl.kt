package com.semicolon.data.datasource

import com.semicolon.data.base.toMultipartList
import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.PostApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.data.remote.response.PostApplicationResponse
import com.semicolon.data.remote.response.PostDetailResponse
import com.semicolon.data.remote.response.PostIdResponse
import com.semicolon.data.remote.response.PostListResponse
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import io.reactivex.Completable
import io.reactivex.Single

class PostDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val postApi: PostApi
) : PostDataSource {

    override fun sendPostImage(postImageParam: PostImageParam): Completable =
        postApi.sendPostImage(
            tokenStorage.getAccessToken(),
            postImageParam.id,
            postImageParam.images.toMultipartList()
        )

    override fun writePost(postParam: PostParam): Single<PostIdResponse> =
        postApi.writePost(tokenStorage.getAccessToken(), postParam.toRequestParam())

    override fun getPostList(): Single<PostListResponse> =
        postApi.getPostList(tokenStorage.getAccessToken())

    override fun fixPost(fixedPostParam: FixedPostParam): Single<PostIdResponse> =
        postApi.fixPost(
            tokenStorage.getAccessToken(),
            fixedPostParam.id,
            fixedPostParam.postParam.toRequestParam()
        )

    override fun deletePost(id: Int): Completable =
        postApi.deletePost(tokenStorage.getAccessToken(), id)

    override fun getPostDetail(id: Int): Single<PostDetailResponse> =
        postApi.getPostDetail(tokenStorage.getAccessToken(), id)

    override fun getPostApplication(id: Int): Single<PostApplicationResponse> =
        postApi.getPostApplication(tokenStorage.getAccessToken(), id)
}