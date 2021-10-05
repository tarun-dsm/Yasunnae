package com.semicolon.data.datasource

import com.semicolon.data.remote.response.MyApplicationResponse
import io.reactivex.Completable
import io.reactivex.Single

interface ApplicationDataSource {

    fun sendApplication(id: Int): Completable

    fun cancelApplication(id: Int): Completable

    fun acceptApplication(id: Int): Completable

    fun getMyApplication(): Single<MyApplicationResponse>
}