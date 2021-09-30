package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.ApplicationApi
import com.semicolon.data.remote.response.MyApplicationResponse
import io.reactivex.Completable
import io.reactivex.Single

class ApplicationDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val applicationApi: ApplicationApi
) : ApplicationDataSource {

    override fun sendApplication(id: Int): Completable =
        applicationApi.sendApplication(tokenStorage.getAccessToken(), id)

    override fun cancelApplication(id: Int): Completable =
        applicationApi.cancelApplication(tokenStorage.getAccessToken(), id)

    override fun acceptApplication(id: Int): Completable =
        applicationApi.acceptApplication(tokenStorage.getAccessToken(), id)

    override fun getMyApplication(): Single<MyApplicationResponse> =
        applicationApi.getMyApplication(tokenStorage.getAccessToken())
}