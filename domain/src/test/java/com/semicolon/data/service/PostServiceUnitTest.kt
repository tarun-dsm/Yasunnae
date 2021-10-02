package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.repository.PostRepository
import com.semicolon.domain.service.PostService
import com.semicolon.domain.service.PostServiceImpl
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class PostServiceUnitTest {

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var postService: PostService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        postService = PostServiceImpl(postRepository, errorHandler)
    }

    @Test
    fun sendPostImageSuccessTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )

        `when`(postRepository.sendPostImage(postImageParam))
            .thenReturn(Completable.complete())

        postService.sendPostImage(postImageParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun sendPostImageErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )

        `when`(postRepository.sendPostImage(postImageParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.sendPostImage(postImageParam).test()
            .assertValue(Resource.error(errorMessage))
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

        `when`(postRepository.writePost(postParam))
            .thenReturn(Single.just(id))

        postService.writePost(postParam).test()
            .assertValue(Resource.success(id))

    }

    @Test
    fun writePostErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
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

        `when`(postRepository.writePost(postParam))
            .thenReturn(Single.just(id))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.writePost(postParam).test()
            .assertValue(Resource.success(id))
    }

    @Test
    fun getPostListSuccessTest() {
        `when`(postRepository.getPostList())
            .thenReturn(Single.just(ArrayList()))

        postService.getPostList().test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getPostListErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED

        `when`(postRepository.getPostList())
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.getPostList().test()
            .assertValue(Resource.error(errorMessage))
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

        `when`(postRepository.fixPost(fixedPostParam))
            .thenReturn(Single.just(id))

        postService.fixPost(fixedPostParam).test()
            .assertValue(Resource.success(id))
    }

    @Test
    fun fixPostErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
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

        `when`(postRepository.fixPost(fixedPostParam))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.fixPost(fixedPostParam).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun deletePostSuccessTest() {
        val id = 1234

        `when`(postRepository.deletePost(id))
            .thenReturn(Completable.complete())

        postService.deletePost(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun deletePostErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
        val id = 1234

        `when`(postRepository.deletePost(id))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.deletePost(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun getPostDetailSuccessTest() {
        val id = 1234
        val postDetail = PostDetailEntity(
            "Shin",
            "Good",
            PostDetailEntity.PostInfo(
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
            PostDetailEntity.PetInfo(
                "Tom",
                "Cat",
                "남",
                ArrayList(),
                AnimalType.MAMMAL
            )
        )

        `when`(postRepository.getPostDetail(id))
            .thenReturn(Single.just(postDetail))

        postService.getPostDetail(id).test()
            .assertValue(Resource.success(postDetail))
    }

    @Test
    fun getPostDetailErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
        val id = 1234

        `when`(postRepository.getPostDetail(id))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.getPostDetail(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun getPostApplicationSuccessTest() {
        val id = 1234

        `when`(postRepository.getPostApplication(id))
            .thenReturn(Single.just(ArrayList()))

        postService.getPostApplication(id).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getPostApplicationErrorTest() {
        val exception = Exception()
        val errorMessage = Error.UNAUTHORIZED
        val id = 1234

        `when`(postRepository.getPostApplication(id))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        postService.getPostApplication(id).test()
            .assertValue(Resource.error(errorMessage))
    }
}