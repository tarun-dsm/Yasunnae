package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.enum.AnimalType
import com.semicolon.domain.param.FixedPostParam
import com.semicolon.domain.param.PostImageParam
import com.semicolon.domain.param.PostParam
import com.semicolon.domain.service.PostService
import com.semicolon.domain.usecase.post.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostUseCasesUnitTest {

    @Mock
    private lateinit var postService: PostService

    private lateinit var deletePostUseCase: DeletePostUseCase
    private lateinit var fixPostUseCase: FixPostUseCase
    private lateinit var getPostApplicationUseCase: GetPostApplicationUseCase
    private lateinit var getPostDetailUseCase: GetPostDetailUseCase
    private lateinit var getPostListUseCase: GetPostListUseCase
    private lateinit var sendPostImageUseCase: SendPostImageUseCase
    private lateinit var writePostUseCase: WritePostUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        deletePostUseCase = DeletePostUseCase(postService)
        fixPostUseCase = FixPostUseCase(postService)
        getPostApplicationUseCase = GetPostApplicationUseCase(postService)
        getPostDetailUseCase = GetPostDetailUseCase(postService)
        getPostListUseCase = GetPostListUseCase(postService)
        sendPostImageUseCase = SendPostImageUseCase(postService)
        writePostUseCase = WritePostUseCase(postService)
    }

    @Test
    fun deletePostTest() {
        val id = 1234

        `when`(postService.deletePost(id))
            .thenReturn(Single.just(Resource.success(Unit)))

        deletePostUseCase.interact(id).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun fixPostTest() {
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

        `when`(postService.fixPost(fixedPostParam))
            .thenReturn(Single.just(Resource.success(id)))

        fixPostUseCase.interact(fixedPostParam).test()
            .assertValue(Resource.success(id))
    }

    @Test
    fun getPostApplicationTest() {
        val id = 1234

        `when`(postService.getPostApplication(id))
            .thenReturn(Single.just(Resource.success(ArrayList())))

        getPostApplicationUseCase.interact(id).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getPostDetailTest() {
        val id = 1234
        val postDetail = PostDetailEntity(
            "Shin",
            "Good",
            isMine = true,
            isApplied = false,
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

        `when`(postService.getPostDetail(id))
            .thenReturn(Single.just(Resource.success(postDetail)))

        getPostDetailUseCase.interact(id).test()
            .assertValue(Resource.success(postDetail))
    }

    @Test
    fun getPostListTest() {
        `when`(postService.getPostList())
            .thenReturn(Single.just(Resource.success(ArrayList())))

        getPostListUseCase.interact(Unit).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun sendPostImageTest() {
        val postImageParam = PostImageParam(
            1234,
            ArrayList()
        )

        `when`(postService.sendPostImage(postImageParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        sendPostImageUseCase.interact(postImageParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun writePostTest() {
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

        `when`(postService.writePost(postParam))
            .thenReturn(Single.just(Resource.success(id)))

        writePostUseCase.interact(postParam).test()
            .assertValue(Resource.success(id))
    }
}