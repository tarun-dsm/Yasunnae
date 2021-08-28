package com.semicolon.domain.repository

import com.semicolon.domain.entity.ApplicationEntity
import io.reactivex.Completable
import io.reactivex.Single

interface ApplicationRepository {

    fun sendApplication(id: Int): Completable

    fun cancelApplication(id: Int): Completable

    fun acceptApplication(id: Int): Completable

    fun getMyApplicationList(): Single<List<ApplicationEntity>>
}