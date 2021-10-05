package com.semicolon.data.repository

import com.semicolon.data.datasource.AuthDataSource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.repository.AuthRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AuthRepositoryUnitTest {

    @Mock
    private lateinit var authDataSource: AuthDataSource

    private lateinit var authRepository: AuthRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        authRepository = AuthRepositoryImpl(authDataSource)
    }

    @Test
    fun loginSuccessTest() {
        val loginParam = LoginParam(
            "shw0471@naver.com",
            "qwerty1234"
        )

        `when`(authDataSource.login(loginParam))
            .thenReturn(Completable.complete())

        authRepository.login(loginParam).test()
            .assertComplete()
    }

    @Test
    fun loginErrorTest() {
        val loginParam = LoginParam(
            "shw0471@naver.com",
            "qwerty1234"
        )
        val exception = Exception()

        `when`(authDataSource.login(loginParam))
            .thenReturn(Completable.error(exception))

        authRepository.login(loginParam).test()
            .assertError(exception)
    }

    @Test
    fun tokenRefreshSuccessTest() {
        `when`(authDataSource.tokenRefresh())
            .thenReturn(Completable.complete())

        authRepository.tokenRefresh().test()
            .assertComplete()
    }

    @Test
    fun tokenRefreshErrorTest() {
        val exception = Exception()

        `when`(authDataSource.tokenRefresh())
            .thenReturn(Completable.error(exception))

        authRepository.tokenRefresh().test()
            .assertError(exception)
    }
}