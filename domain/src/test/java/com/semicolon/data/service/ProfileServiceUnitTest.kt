package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.repository.ProfileRepository
import com.semicolon.domain.service.ProfileService
import com.semicolon.domain.service.ProfileServiceImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ProfileServiceUnitTest {

    @Mock
    private lateinit var profileRepository: ProfileRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var profileService: ProfileService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        profileService = ProfileServiceImpl(profileRepository, errorHandler)
    }

    @Test
    fun getProfileSuccessTest() {
        val id = 1234
        val profile = ProfileEntity(
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

        `when`(profileRepository.getProfile(id))
            .thenReturn(Single.just(profile))

        profileService.getProfile(id).test()
            .assertValue(Resource.success(profile))
    }

    @Test
    fun getProfileErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NOT_FOUND
        val id = 1234

        `when`(profileRepository.getProfile(id))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        profileService.getProfile(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun getReviewSuccessTest() {
        val id = 1234

        `when`(profileRepository.getReview(id))
            .thenReturn(Single.just(ArrayList()))

        profileService.getReview(id).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getReviewErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NOT_FOUND
        val id = 1234

        `when`(profileRepository.getReview(id))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        profileService.getReview(id).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun getProfilePostSuccessTest() {
        val id = 1234

        `when`(profileRepository.getProfilePost(id))
            .thenReturn(Single.just(ArrayList()))

        profileService.getProfilePost(id).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getProfilePostErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NOT_FOUND
        val id = 1234

        `when`(profileRepository.getProfilePost(id))
            .thenReturn(Single.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        profileService.getProfilePost(id).test()
            .assertValue(Resource.error(errorMessage))
    }
}