package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ProfileApi
import com.semicolon.data.remote.response.ProfilePostResponse
import com.semicolon.data.remote.response.ProfileResponse
import com.semicolon.data.remote.response.ProfileReviewResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ProfileDataSourceUnitTest {

    @Mock
    private lateinit var profileApi: ProfileApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var profileDataSource: ProfileDataSource

    private val token = "abc.def.ghi"

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        profileDataSource = ProfileDataSourceImpl(tokenStorage, profileApi)

        `when`(tokenStorage.getAccessToken())
            .thenReturn(token)
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

        `when`(profileApi.getProfile(token, id))
            .thenReturn(Single.just(profile))

        profileDataSource.getProfile(id).test()
            .assertValue(profile)
    }

    @Test
    fun getProfileErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileApi.getProfile(token, id))
            .thenReturn(Single.error(exception))

        profileDataSource.getProfile(id).test()
            .assertError(exception)
    }

    @Test
    fun getProfileWithoutIdSuccessTest() {
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

        `when`(profileApi.getMyProfile(token))
            .thenReturn(Single.just(profile))

        profileDataSource.getProfile(null).test()
            .assertValue(profile)
    }

    @Test
    fun getProfileWithoutIdErrorTest() {
        val exception = Exception()

        `when`(profileApi.getMyProfile(token))
            .thenReturn(Single.error(exception))

        profileDataSource.getProfile(null).test()
            .assertError(exception)
    }

    @Test
    fun getReviewSuccessTest() {
        val id = 1234
        val review = ProfileReviewResponse(ArrayList())

        `when`(profileApi.getProfileReview(token, id))
            .thenReturn(Single.just(review))

        profileDataSource.getReview(id).test()
            .assertValue(review)
    }

    @Test
    fun getReviewErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileApi.getProfileReview(token, id))
            .thenReturn(Single.error(exception))

        profileDataSource.getReview(id).test()
            .assertError(exception)
    }

    @Test
    fun getReviewWithoutIdSuccessTest() {
        val review = ProfileReviewResponse(ArrayList())

        `when`(profileApi.getMyProfileReview(token))
            .thenReturn(Single.just(review))

        profileDataSource.getReview(null).test()
            .assertValue(review)
    }

    @Test
    fun getReviewWithoutIdErrorTest() {
        val exception = Exception()

        `when`(profileApi.getMyProfileReview(token))
            .thenReturn(Single.error(exception))

        profileDataSource.getReview(null).test()
            .assertError(exception)
    }

    @Test
    fun getProfilePostSuccessTest() {
        val id = 1234
        val post = ProfilePostResponse(ArrayList())

        `when`(profileApi.getProfilePost(token, id))
            .thenReturn(Single.just(post))

        profileDataSource.getProfilePost(id).test()
            .assertValue(post)
    }

    @Test
    fun getProfilePostErrorTest() {
        val id = 1234
        val exception = Exception()

        `when`(profileApi.getProfilePost(token, id))
            .thenReturn(Single.error(exception))

        profileDataSource.getProfilePost(id).test()
            .assertError(exception)
    }

    @Test
    fun getProfilePostWithoutIdSuccessTest() {
        val post = ProfilePostResponse(ArrayList())

        `when`(profileApi.getMyProfilePost(token))
            .thenReturn(Single.just(post))

        profileDataSource.getProfilePost(null).test()
            .assertValue(post)
    }

    @Test
    fun getProfilePostWithoutIdErrorTest() {
        val exception = Exception()

        `when`(profileApi.getMyProfilePost(token))
            .thenReturn(Single.error(exception))

        profileDataSource.getProfilePost(null).test()
            .assertError(exception)
    }
}