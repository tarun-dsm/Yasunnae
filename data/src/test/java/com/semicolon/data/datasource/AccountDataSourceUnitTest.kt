package com.semicolon.data.datasource

import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.AccountApi
import com.semicolon.data.remote.request.ReportRequest
import com.semicolon.data.remote.request.toRequestParam
import com.semicolon.data.remote.response.TokenResponse
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.param.Sex
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AccountDataSourceUnitTest {

    @Mock
    private lateinit var accountApi: AccountApi

    @Mock
    private lateinit var tokenStorage: TokenStorage

    private lateinit var accountDataSource: AccountDataSource

    private val token = "abc.def.ghi"

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        accountDataSource = AccountDataSourceImpl(tokenStorage, accountApi)

        `when`(tokenStorage.getAccessToken())
            .thenReturn(token)
    }

    @Test
    fun registerAccountSuccessTest() {
        val registerAccountParam = RegisterAccountParam(
            "shw0471@naver.com",
            "qwerty1234",
            "Shin",
            18,
            Sex.MALE,
            true,
            "yeah"
        )
        val accessToken = "abc.def.ghi"
        val refreshToken = "jkl.mno.pqr"

        `when`(accountApi.registerAccount(registerAccountParam.toRequestParam()))
            .thenReturn(Single.just(TokenResponse(accessToken, refreshToken)))

        accountDataSource.registerAccount(registerAccountParam).test()
            .assertComplete()

        verify(tokenStorage).saveToken(accessToken, refreshToken)
    }

    @Test
    fun registerAccountErrorTest() {
        val registerAccountParam = RegisterAccountParam(
            "shw0471@naver.com",
            "qwerty1234",
            "Shin",
            18,
            Sex.MALE,
            true,
            "yeah"
        )
        val exception = Exception()

        `when`(accountApi.registerAccount(registerAccountParam.toRequestParam()))
            .thenReturn(Single.error(exception))

        accountDataSource.registerAccount(registerAccountParam).test()
            .assertError(exception)
    }

    @Test
    fun checkEmailDuplicationSuccessTest() {
        val email = "shw0471@naver.com"

        `when`(accountApi.checkEmailDuplication(email))
            .thenReturn(Completable.complete())

        accountDataSource.checkEmailDuplication(email).test()
            .assertComplete()
    }

    @Test
    fun checkEmailDuplicationErrorTest() {
        val email = "shw0471@naver.com"
        val exception = Exception()

        `when`(accountApi.checkEmailDuplication(email))
            .thenReturn(Completable.error(exception))

        accountDataSource.checkEmailDuplication(email).test()
            .assertError(exception)
    }

    @Test
    fun checkNicknameDuplicationSuccessTest() {
        val nickname = "Shin"

        `when`(accountApi.checkNicknameDuplication(nickname))
            .thenReturn(Completable.complete())

        accountDataSource.checkNicknameDuplication(nickname).test()
            .assertComplete()
    }

    @Test
    fun checkNicknameDuplicationErrorTest() {
        val nickname = "Shin"
        val exception = Exception()

        `when`(accountApi.checkNicknameDuplication(nickname))
            .thenReturn(Completable.error(exception))

        accountDataSource.checkNicknameDuplication(nickname).test()
            .assertError(exception)
    }

    @Test
    fun saveCoordinateSuccessTest() {
        val coordinateParam = CoordinateParam(
            1.2345,
            1.2345
        )

        `when`(accountApi.saveCoordinate(token, coordinateParam.toRequestParam()))
            .thenReturn(Completable.complete())

        accountDataSource.saveCoordinate(coordinateParam).test()
            .assertComplete()
    }

    @Test
    fun saveCoordinateErrorTest() {
        val coordinateParam = CoordinateParam(
            1.2345,
            1.2345
        )
        val exception = Exception()

        `when`(accountApi.saveCoordinate(token, coordinateParam.toRequestParam()))
            .thenReturn(Completable.error(exception))

        accountDataSource.saveCoordinate(coordinateParam).test()
            .assertError(exception)
    }

    @Test
    fun reportUserSuccessTest() {
        val reportParam = ReportParam(
            1234,
            "comment"
        )

        `when`(
            accountApi.reportUser(
                token, reportParam.id, ReportRequest(reportParam.comment)
            )
        ).thenReturn(Completable.complete())

        accountDataSource.reportUser(reportParam).test()
            .assertComplete()
    }

    @Test
    fun reportUserErrorTest() {
        val reportParam = ReportParam(
            1234,
            "comment"
        )
        val exception = Exception()

        `when`(
            accountApi.reportUser(
                token, reportParam.id, ReportRequest(reportParam.comment)
            )
        ).thenReturn(Completable.error(exception))

        accountDataSource.reportUser(reportParam).test()
            .assertError(exception)
    }
}