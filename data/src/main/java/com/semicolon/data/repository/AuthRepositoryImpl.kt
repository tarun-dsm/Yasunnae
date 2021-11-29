package com.semicolon.data.repository

import com.semicolon.data.datasource.AuthDataSource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.repository.AuthRepository
import io.reactivex.Completable

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun login(loginParam: LoginParam): Completable =
        authDataSource.login(loginParam)

    override fun tokenRefresh(): Completable =
        authDataSource.tokenRefresh()

    override fun logout(): Completable =
        authDataSource.logout()
}