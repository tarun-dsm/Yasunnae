package com.semicolon.data.repository

import com.semicolon.data.datasource.PostDataSource
import com.semicolon.data.remote.response.*
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.repository.PostRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class PostRepositoryUnitTest {

    @Mock
    private lateinit var postDataSource: PostDataSource

    private lateinit var postRepository: PostRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        postRepository = PostRepositoryImpl(postDataSource)
    }

    @Test
    fun sendPostImageSuccessTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )

        `when`(postDataSource.sendPostImage(postImageParam))
            .thenReturn(Completable.complete())

        postRepository.sendPostImage(postImageParam).test()
            .assertComplete()
    }

    @Test
    fun sendPostImageErrorTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )
        val exception = Exception()

        `when`(postDataSource.sendPostImage(postImageParam))
            .thenReturn(Completable.error(exception))

        postRepository.sendPostImage(postImageParam).test()
            .assertError(exception)
    }

    @Test
    fun writePostSuccessTest() {
        val id = 1234
        val postParam = PostParam(
            "Yeah",
            "2021-09-01",
            "2021-09-02",
            "2021-08-31",
            "Hi",
            "010-0000-0000",
            "Tom",
            "Cat",
            "남",
            AnimalType.MAMMAL
        )

        `when`(postDataSource.writePost(postParam))
            .thenReturn(Single.just(PostIdResponse(id)))

        postRepository.writePost(postParam).test()
            .assertValue(id)
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
            "남",
            AnimalType.MAMMAL
        )
        val exception = Exception()

        `when`(postDataSource.writePost(postParam))
            .thenReturn(Single.error(exception))

        postRepository.writePost(postParam).test()
            .assertError(exception)
    }

    @Test
    fun getPostListSuccessTest() {
        val postListResponse = PostListResponse(
            ArrayList()
        )

        `when`(postDataSource.getPostList())
            .thenReturn(Single.just(postListResponse))

        postRepository.getPostList().test()
            .assertValue(postListResponse.posts.map { it.toEntity() })
    }

    @Test
    fun getPostListErrorTest() {
        val exception = Exception()

        `when`(postDataSource.getPostList())
            .thenReturn(Single.error(exception))

        postRepository.getPostList().test()
            .assertError(exception)
    }

    @Test
    fun fixPostSuccessTest() {
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
                "여",
                AnimalType.MAMMAL
            )
        )

        `when`(postDataSource.fixPost(fixedPostParam))
            .thenReturn(Single.just(PostIdResponse(id)))

        postRepository.fixPost(fixedPostParam).test()
            .assertValue(id)
    }

    @Test
    fun fixPostErrorTest() {
        val fixedPostParam = FixedPostParam(
            1234,
            PostParam(
                "Hi",
                "2021-09-01",
                "2021-09-02",
                "2021-08-31",
                "Yeah",
                "010-0000-0000",
                "Tom",
                "Cat",
                "여",
                AnimalType.MAMMAL
            )
        )
        val exception = Exception()

        `when`(postDataSource.fixPost(fixedPostParam))
            .thenReturn(Single.error(exception))

        postRepository.fixPost(fixedPostParam).test()
            .assertError(exception)
    }

    @Test
    fun deletePostSuccessTest() {
        val id = 1234

        `when`(postDataSource.deletePost(id))
            .thenReturn(Completable.complete())

        postRepository.deletePost(id).test()
            .assertComplete()
    }

    @Test
    fun deletePostErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postDataSource.deletePost(id))
            .thenReturn(Completable.error(exception))

        postRepository.deletePost(id).test()
            .assertError(exception)
    }

    @Test
    fun getPostDetailSuccessTest() {
        val id = 1234
        val postDetail = PostDetailResponse(
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
                "남",
                ArrayList(),
                "MAMMAL"
            )
        )

        `when`(postDataSource.getPostDetail(id))
            .thenReturn(Single.just(postDetail))

        postRepository.getPostDetail(id).test()
            .assertValue(postDetail.toEntity())
    }

    @Test
    fun getPostDetailErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postDataSource.getPostDetail(id))
            .thenReturn(Single.error(exception))

        postRepository.getPostDetail(id).test()
            .assertError(exception)
    }

    @Test
    fun getPostApplicationSuccessTest() {
        val id = 1234
        val postApplicationResponse = PostApplicationResponse(
            ArrayList()
        )

        `when`(postDataSource.getPostApplication(id))
            .thenReturn(Single.just(postApplicationResponse))

        postRepository.getPostApplication(id).test()
            .assertValue(postApplicationResponse.applications.map { it.toEntity() })
    }

    @Test
    fun getPostApplicationErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(postDataSource.getPostApplication(id))
            .thenReturn(Single.error(exception))

        postRepository.getPostApplication(id).test()
            .assertError(exception)
    }
}