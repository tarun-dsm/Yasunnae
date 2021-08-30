package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.service.ProfileService
import com.semicolon.domain.usecase.profile.GetProfilePostUseCase
import com.semicolon.domain.usecase.profile.GetProfileUseCase
import com.semicolon.domain.usecase.profile.GetReviewUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ProfileUseCasesUnitTest {

    @Mock
    private lateinit var profileService: ProfileService

    private lateinit var getProfilePostUseCase: GetProfilePostUseCase
    private lateinit var getProfileUseCase: GetProfileUseCase
    private lateinit var getReviewUseCase: GetReviewUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        getProfilePostUseCase = GetProfilePostUseCase(profileService)
        getProfileUseCase = GetProfileUseCase(profileService)
        getReviewUseCase = GetReviewUseCase(profileService)
    }

    @Test
    fun getProfilePostTest() {
        val id = 1234

        `when`(profileService.getProfilePost(id))
            .thenReturn(Single.just(Resource.success(ArrayList())))

        getProfilePostUseCase.interact(id).test()
            .assertValue(Resource.success(ArrayList()))
    }

    @Test
    fun getProfileUseCase() {
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

        `when`(profileService.getProfile(id))
            .thenReturn(Single.just(Resource.success(profile)))

        getProfileUseCase.interact(id).test()
            .assertValue(Resource.success(profile))
    }

    @Test
    fun getReviewUseCase() {
        val id = 1234

        `when`(profileService.getReview(id))
            .thenReturn(Single.just(Resource.success(ArrayList())))

        getReviewUseCase.interact(id).test()
            .assertValue(Resource.success(ArrayList()))
    }
}