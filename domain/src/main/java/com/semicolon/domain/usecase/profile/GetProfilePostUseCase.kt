package com.semicolon.domain.usecase.profile

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.domain.service.ProfileService
import io.reactivex.Single

class GetProfilePostUseCase(
    private val profileService: ProfileService
) : UseCase<Int, Resource<List<ProfilePostEntity>>>() {

    override fun interact(data: Int?): Single<Resource<List<ProfilePostEntity>>> =
        profileService.getProfilePost(data)
}