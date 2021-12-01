package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.AuthApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.domain.param.LoginParam
import io.reactivex.Completable

class AuthDataSourceImpl(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthApi
) : AuthDataSource {

    override fun login(loginParam: LoginParam): Completable =
        authApi.login(loginParam.toRequestParam())
            .map { tokenStorage.saveToken(it.accessToken, it.refreshToken) }.ignoreElement()

    override fun tokenRefresh(): Completable =
        authApi.tokenRefresh(tokenStorage.getRefreshToken())
            .map { tokenStorage.saveToken(it.accessToken, it.refreshToken) }.ignoreElement()

    override fun logout(): Completable {
        tokenStorage.deleteToken()
        return authApi.logout(tokenStorage.getAccessToken())
    }
}