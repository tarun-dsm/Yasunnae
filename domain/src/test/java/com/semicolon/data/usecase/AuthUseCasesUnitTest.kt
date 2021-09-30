package com.semicolon.data.usecase

import android.util.Log
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.LoginParam
import com.semicolon.domain.service.AuthService
import com.semicolon.domain.usecase.auth.LoginUseCase
import com.semicolon.domain.usecase.auth.TokenRefreshUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.internal.configuration.MockAnnotationProcessor
import kotlin.math.log

class AuthUseCasesUnitTest {

    @Mock
    private lateinit var authService: AuthService

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var tokenRefreshUseCase: TokenRefreshUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(authService)
        tokenRefreshUseCase = TokenRefreshUseCase(authService)
    }

    @Test
    fun loginTest() {
        val loginParam = LoginParam(
            "shw0471@naver.com",
            "qwerty1234"
        )

        `when`(authService.login(loginParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        loginUseCase.interact(loginParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun tokenRefreshTest() {
        `when`(authService.tokenRefresh())
            .thenReturn(Single.just(Resource.success(Unit)))

        tokenRefreshUseCase.interact(Unit).test()
            .assertValue(Resource.success(Unit))
    }
}