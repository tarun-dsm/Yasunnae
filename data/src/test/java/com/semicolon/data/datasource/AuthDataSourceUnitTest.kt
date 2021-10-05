package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.AuthApi
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.data.remote.response.AccessTokenResponse
import com.semicolon.data.remote.response.TokenResponse
import com.semicolon.domain.param.LoginParam
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AuthDataSourceUnitTest {

    @Mock
    private lateinit var authApi: AuthApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var authDataSource: AuthDataSource

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        authDataSource = AuthDataSourceImpl(tokenStorage, authApi)
    }

    @Test
    fun loginSuccessTest() {
        val loginParam = LoginParam(
            "shw0471@naver.com",
            "qwerty1234"
        )
        val accessToken = "abc.def.ghi"
        val refreshToken = "jkl.mno.pqr"

        `when`(authApi.login(loginParam.toRequestParam()))
            .thenReturn(Single.just(TokenResponse(accessToken, refreshToken)))

        authDataSource.login(loginParam).test()
            .assertComplete()

        verify(tokenStorage).saveToken(accessToken, refreshToken)
    }

    @Test
    fun loginErrorTest() {
        val loginParam = LoginParam(
            "shw0471@naver.com",
            "qwerty1234"
        )
        val exception = Exception()

        `when`(authApi.login(loginParam.toRequestParam()))
            .thenReturn(Single.error(exception))

        authDataSource.login(loginParam).test()
            .assertError(exception)
    }

    @Test
    fun tokenRefreshSuccessTest() {
        val accessToken = "abc.def.ghi"
        val refreshToken = "jkl.mno.pqr"

        `when`(authApi.tokenRefresh(refreshToken))
            .thenReturn(Single.just(TokenResponse(accessToken, refreshToken)))

        `when`(tokenStorage.getRefreshToken())
            .thenReturn(refreshToken)

        authDataSource.tokenRefresh().test()
            .assertComplete()

        verify(tokenStorage).saveToken(accessToken, refreshToken)
    }

    @Test
    fun tokenRefreshErrorTest() {
        val refreshToken = "jkl.mno.pqr"
        val exception = Exception()

        `when`(authApi.tokenRefresh(refreshToken))
            .thenReturn(Single.error(exception))

        `when`(tokenStorage.getRefreshToken())
            .thenReturn(refreshToken)

        authDataSource.tokenRefresh().test()
            .assertError(exception)
    }
}