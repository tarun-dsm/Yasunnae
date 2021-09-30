package com.semicolon.data.repository

import com.semicolon.data.datasource.ApplicationDataSource
import com.semicolon.data.remote.response.toEntity
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.repository.ApplicationRepository
import io.reactivex.Completable
import io.reactivex.Single

class ApplicationRepositoryImpl(
    private val applicationDataSource: ApplicationDataSource
) : ApplicationRepository {

    override fun sendApplication(id: Int): Completable =
        applicationDataSource.sendApplication(id)

    override fun cancelApplication(id: Int): Completable =
        applicationDataSource.cancelApplication(id)

    override fun acceptApplication(id: Int): Completable =
        applicationDataSource.acceptApplication(id)

    override fun getMyApplicationList(): Single<List<ApplicationEntity>> =
        applicationDataSource.getMyApplication()
            .map { response -> response.myApplication.map { it.toEntity() } }
}