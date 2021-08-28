package com.semicolon.domain.usecase.application

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.service.ApplicationService
import io.reactivex.Single

class GetMyApplicationListUseCase(
    private val applicationService: ApplicationService
) : UseCase<Unit, Resource<List<ApplicationEntity>>>() {

    override fun interact(data: Unit?): Single<Resource<List<ApplicationEntity>>> =
        applicationService.getMyApplicationList()
}