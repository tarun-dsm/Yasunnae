package com.semicolon.data.repository

import com.semicolon.data.datasource.ProfileDataSource
import com.semicolon.data.remote.response.ProfilePostResponse
import com.semicolon.data.remote.response.ProfileResponse
import com.semicolon.data.remote.response.ProfileReviewResponse
import com.semicolon.data.remote.response.toEntity
import com.semicolon.domain.repository.ProfileRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ProfileRepositoryUnitTest {

    @Mock
    private lateinit var profileDataSource: ProfileDataSource

    private lateinit var profileRepository: ProfileRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        profileRepository = ProfileRepositoryImpl(profileDataSource)
    }

    @Test
    fun getProfileSuccessTest() {
        val id = 1234
        val profile = ProfileResponse(
            "Shin",
            5.0,
            "good",
            18,
            "남",
            "Yeah",
            true,
            "haha",
            "NewYork"
        )

        `when`(profileDataSource.getProfile(id))
            .thenReturn(Single.just(profile))

        profileRepository.getProfile(id).test()
            .assertValue(profile.toEntity())
    }

    @Test
    fun getProfileErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileDataSource.getProfile(id))
            .thenReturn(Single.error(exception))

        profileRepository.getProfile(id).test()
            .assertError(exception)
    }

    @Test
    fun getReviewSuccessTest() {
        val id = 1234
        val review = ProfileReviewResponse(ArrayList())

        `when`(profileDataSource.getReview(id))
            .thenReturn(Single.just(review))

        profileRepository.getReview(id).test()
            .assertValue(review.reviews.map { it.toEntity() })
    }

    @Test
    fun getReviewErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileDataSource.getReview(id))
            .thenReturn(Single.error(exception))

        profileRepository.getReview(id).test()
            .assertError(exception)
    }

    @Test
    fun getProfilePostSuccessTest() {
        val id = 1234
        val profilePost = ProfilePostResponse(ArrayList())

        `when`(profileDataSource.getProfilePost(id))
            .thenReturn(Single.just(profilePost))

        profileRepository.getProfilePost(id).test()
            .assertValue(profilePost.posts.map { it.toEntity() })
    }

    @Test
    fun getProfilePostErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileDataSource.getProfilePost(id))
            .thenReturn(Single.error(exception))

        profileRepository.getProfilePost(id).test()
            .assertError(exception)
    }
}