package com.semicolon.data.datasource

import com.semicolon.data.remote.request.LoginRequest
import com.semicolon.domain.param.LoginParam
import io.reactivex.Completable

interface AuthDataSource {

    fun login(loginParam: LoginParam): Completable

    fun tokenRefresh(): Completable

    fun logout(): Completable
}