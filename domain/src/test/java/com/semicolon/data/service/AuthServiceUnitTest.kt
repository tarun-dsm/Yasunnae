package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.repository.AuthRepository
import com.semicolon.domain.service.AuthService
import com.semicolon.domain.service.AuthServiceImpl
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AuthServiceUnitTest {

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var authService: AuthService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        authService = AuthServiceImpl(authRepository, errorHandler)
    }

    @Test
    fun loginSuccessTest() {
        val loginParam = LoginParam("shw0471@naver.com", "qwerty1234")

        `when`(authRepository.login(loginParam))
            .thenReturn(Completable.complete())

        authService.login(loginParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun loginErrorTest() {
        val exception = Exception()
        val errorMessage = Error.BAD_REQUEST
        val loginParam = LoginParam("shw0471@naver.com", "qwerty1234")

        `when`(authRepository.login(loginParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        authService.login(loginParam).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun tokenRefreshSuccessTest() {
        `when`(authRepository.tokenRefresh())
            .thenReturn(Completable.complete())

        authService.tokenRefresh().test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun tokenRefreshErrorTest() {
        val exception = Exception()
        val errorMessage = Error.BAD_REQUEST

        `when`(authRepository.tokenRefresh())
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        authService.tokenRefresh().test()
            .assertValue(Resource.error(errorMessage))
    }
}