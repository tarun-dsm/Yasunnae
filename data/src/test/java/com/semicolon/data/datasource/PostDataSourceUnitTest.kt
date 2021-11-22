package com.semicolon.data.datasource

import com.semicolon.data.base.toMultipartList
import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.PostApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.data.remote.response.*
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.enum.Sex
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class PostDataSourceUnitTest {

    @Mock
    private lateinit var postApi: PostApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var postDataSource: PostDataSource

    private val token = "abc.def.ghi"

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        postDataSource = PostDataSourceImpl(tokenStorage, postApi)

        `when`(tokenStorage.getAccessToken())
            .thenReturn(token)
    }

    @Test
    fun sendPostImageSuccessTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )

        `when`(
            postApi.sendPostImage(
                token,
                postImageParam.id,
                postImageParam.images.toMultipartList()
            )
        ).thenReturn(Completable.complete())

        postDataSource.sendPostImage(postImageParam).test()
            .assertComplete()
    }

    @Test
    fun sendPostImageErrorTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )
        val exception = Exception()

        `when`(
            postApi.sendPostImage(
                token,
                postImageParam.id,
                postImageParam.images.toMultipartList()
            )
        ).thenReturn(Completable.error(exception))

        postDataSource.sendPostImage(postImageParam).test()
            .assertError(exception)
    }

    @Test
    fun writePostSuccessTest() {
        val postIdResponse = PostIdResponse(1234)
        val postParam = PostParam(
            "Yeah",
            "2021-09-01",
            "2021-09-02",
            "2021-08-31",
            "Hi",
            "010-0000-0000",
            "Tom",
            "Cat",
            Sex.MALE,
            AnimalType.MAMMEL
        )

        `when`(postApi.writePost(token, postParam.toRequestParam()))
            .thenReturn(Single.just(postIdResponse))

        postDataSource.writePost(postParam).test()
            .assertValue(postIdResponse)
    }

    @Test
    fun writePostErrorTest() {
        val postParam = PostParam(
            "Yeah",
            "2021-09-01",
            "2021-09-02",
            "2021-08-31",
            "Hi",
            "010-0000-0000",
            "Tom",
            "Cat",
            Sex.MALE,
            AnimalType.MAMMEL
        )
        val exception = Exception()

        `when`(postApi.writePost(token, postParam.toRequestParam()))
            .thenReturn(Single.error(exception))

        postDataSource.writePost(postParam).test()
            .assertError(exception)
    }

    @Test
    fun getPostListSuccessTest() {
        val postListResponse = PostListResponse(
            ArrayList()
        )

        `when`(postApi.getPostList(token))
            .thenReturn(Single.just(postListResponse))

        postDataSource.getPostList().test()
            .assertValue(postListResponse)
    }

    @Test
    fun getPostListErrorTest() {
        val exception = Exception()

        `when`(postApi.getPostList(token))
            .thenReturn(Single.error(exception))

        postDataSource.getPostList().test()
            .assertError(exception)
    }

    @Test
    fun fixPostSuccessTest() {
        val id = 1234
        val postIdResponse = PostIdResponse(id)
        val fixedPostParam = FixedPostParam(
            id,
            PostParam(
                "Hi",
                "2021-09-01",
                "2021-09-02",
                "2021-08-31",
                "Yeah",
                "010-0000-0000",
                "Tom",
                "Cat",
                Sex.FEMALE,
                AnimalType.MAMMEL
            )
        )

        `when`(postApi.fixPost(token, id, fixedPostParam.postParam.toRequestParam()))
            .thenReturn(Single.just(postIdResponse))

        postDataSource.fixPost(fixedPostParam).test()
            .assertValue(postIdResponse)
    }

    @Test
    fun fixPostErrorTest() {
        val id = 1234
        val fixedPostParam = FixedPostParam(
            id,
            PostParam(
                "Hi",
                "2021-09-01",
                "2021-09-02",
                "2021-08-31",
                "Yeah",
                "010-0000-0000",
                "Tom",
                "Cat",
                Sex.FEMALE,
                AnimalType.MAMMEL
            )
        )
        val exception = Exception()

        `when`(postApi.fixPost(token, id, fixedPostParam.postParam.toRequestParam()))
            .thenReturn(Single.error(exception))

        postDataSource.fixPost(fixedPostParam).test()
            .assertError(exception)
    }

    @Test
    fun deletePostSuccessTest() {
        val id = 1234

        `when`(postApi.deletePost(token, id))
            .thenReturn(Completable.complete())

        postDataSource.deletePost(id).test()
            .assertComplete()
    }

    @Test
    fun deletePostErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postApi.deletePost(token, id))
            .thenReturn(Completable.error(exception))

        postDataSource.deletePost(id).test()
            .assertError(exception)
    }

    @Test
    fun getPostDetailSuccessTest() {
        val id = 1234
        val postDetail = PostDetailResponse(
            1,
            "Shin",
            "Good",
            isMine = true,
            isApplied = false,
            post = PostDetail(
                "Hi",
                "2021-09-01",
                "2021-09-02",
                "2021-08-31",
                "Hello",
                "010-0000-0000",
                "2021-08-30",
                isApplicationEnd = false,
                isUpdated = false
            ),
            pet = PetDetail(
                "Tom",
                "Cat",
                "MALE",
                ArrayList(),
                "MAMMEL"
            )
        )

        `when`(postApi.getPostDetail(token, id))
            .thenReturn(Single.just(postDetail))

        postDataSource.getPostDetail(id).test()
            .assertValue(postDetail)
    }

    @Test
    fun getPostDetailErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postApi.getPostDetail(token, id))
            .thenReturn(Single.error(exception))

        postDataSource.getPostDetail(id).test()
            .assertError(exception)
    }

    @Test
    fun getPostApplicationSuccessTest() {
        val id = 1234
        val postApplicationResponse = PostApplicationResponse(
            ArrayList()
        )

        `when`(postApi.getPostApplication(token, id))
            .thenReturn(Single.just(postApplicationResponse))

        postDataSource.getPostApplication(id).test()
            .assertValue(postApplicationResponse)
    }

    @Test
    fun getPostApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postApi.getPostApplication(token, id))
            .thenReturn(Single.error(exception))

        postDataSource.getPostApplication(id).test()
            .assertError(exception)
    }
}