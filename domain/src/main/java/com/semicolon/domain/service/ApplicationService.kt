package com.semicolon.domain.service

import com.semicolon.domain.base.Resource
import com.semicolon.domain.entity.ApplicationEntity
import io.reactivex.Single

interface ApplicationService {

    fun sendApplication(id: Int): Single<Resource<Unit>>

    fun cancelApplication(id: Int): Single<Resource<Unit>>

    fun acceptApplication(id: Int): Single<Resource<Unit>>

    fun getMyApplicationList(): Single<Resource<List<ApplicationEntity>>>
}