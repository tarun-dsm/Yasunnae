package com.semicolon.domain.service

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.base.toResource
import com.semicolon.domain.base.toSingleResource
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.repository.ApplicationRepository
import io.reactivex.Single

class ApplicationServiceImpl(
    private val applicationRepository: ApplicationRepository,
    private val errorHandler: ErrorHandler
) : ApplicationService {

    override fun sendApplication(id: Int): Single<Resource<Unit>> =
        applicationRepository.sendApplication(id).toSingleResource(errorHandler)

    override fun cancelApplication(id: Int): Single<Resource<Unit>> =
        applicationRepository.cancelApplication(id).toSingleResource(errorHandler)

    override fun acceptApplication(id: Int): Single<Resource<Unit>> =
        applicationRepository.acceptApplication(id).toSingleResource(errorHandler)

    override fun getMyApplicationList(): Single<Resource<List<ApplicationEntity>>> =
        applicationRepository.getMyApplicationList().toResource(errorHandler)
}