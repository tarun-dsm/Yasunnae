package com.semicolon.domain.usecase.profile

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.ProfileEntity
import com.semicolon.domain.service.ProfileService
import io.reactivex.Single

class GetProfileUseCase(
    private val profileService: ProfileService
) : UseCase<Int, Resource<ProfileEntity>>() {

    override fun interact(data: Int?): Single<Resource<ProfileEntity>> =
        profileService.getProfile(data)
}