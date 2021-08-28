package com.semicolon.domain.usecase.application

import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.UseCase
import com.semicolon.domain.service.ApplicationService
import io.reactivex.Single

class SendApplicationUseCase(
    private val applicationService: ApplicationService
) : UseCase<Int, Resource<Unit>>() {

    override fun interact(data: Int): Single<Resource<Unit>> =
        applicationService.sendApplication(data)
}