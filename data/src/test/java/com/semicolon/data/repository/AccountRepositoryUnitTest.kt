package com.semicolon.data.repository

import com.semicolon.data.datasource.AccountDataSource
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.param.Sex
import com.semicolon.domain.repository.AccountRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AccountRepositoryUnitTest {

    @Mock
    private lateinit var accountDataSource: AccountDataSource

    private lateinit var accountRepository: AccountRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        accountRepository = AccountRepositoryImpl(accountDataSource)
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

        `when`(accountDataSource.registerAccount(registerAccountParam))
            .thenReturn(Completable.complete())

        accountRepository.registerAccount(registerAccountParam).test()
            .assertComplete()
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

        `when`(accountDataSource.registerAccount(registerAccountParam))
            .thenReturn(Completable.error(exception))

        accountRepository.registerAccount(registerAccountParam).test()
            .assertError(exception)
    }

    @Test
    fun checkEmailDuplicationSuccessTest() {
        val email = "shw0471@naver.com"

        `when`(accountDataSource.checkEmailDuplication(email))
            .thenReturn(Completable.complete())

        accountRepository.checkEmailDuplication(email).test()
            .assertComplete()
    }

    @Test
    fun checkEmailDuplicationErrorTest() {
        val email = "shw0471@naver.com"
        val exception = Exception()

        `when`(accountDataSource.checkEmailDuplication(email))
            .thenReturn(Completable.error(exception))

        accountRepository.checkEmailDuplication(email).test()
            .assertError(exception)
    }

    @Test
    fun checkNicknameDuplicationSuccessTest() {
        val nickname = "Shin"

        `when`(accountDataSource.checkNicknameDuplication(nickname))
            .thenReturn(Completable.complete())

        accountRepository.checkNicknameDuplication(nickname).test()
            .assertComplete()
    }

    @Test
    fun checkNicknameDuplicationErrorTest() {
        val nickname = "Shin"
        val exception = Exception()

        `when`(accountDataSource.checkNicknameDuplication(nickname))
            .thenReturn(Completable.error(exception))

        accountRepository.checkNicknameDuplication(nickname).test()
            .assertError(exception)
    }

    @Test
    fun saveCoordinateSuccessTest() {
        val coordinateParam = CoordinateParam(
            1.234,
            1.234
        )

        `when`(accountDataSource.saveCoordinate(coordinateParam))
            .thenReturn(Completable.complete())

        accountRepository.saveCoordinate(coordinateParam).test()
            .assertComplete()
    }

    @Test
    fun saveCoordinateErrorTest() {
        val coordinateParam = CoordinateParam(
            1.234,
            1.234
        )
        val exception = Exception()

        `when`(accountDataSource.saveCoordinate(coordinateParam))
            .thenReturn(Completable.error(exception))

        accountRepository.saveCoordinate(coordinateParam).test()
            .assertError(exception)
    }

    @Test
    fun reportUserSuccessTest() {
        val reportParam = ReportParam(1234, "hi")

        `when`(accountDataSource.reportUser(reportParam))
            .thenReturn(Completable.complete())

        accountRepository.reportUser(reportParam).test()
            .assertComplete()
    }

    @Test
    fun reportUserErrorTest() {
        val reportParam = ReportParam(1234, "hi")
        val exception = Exception()

        `when`(accountDataSource.reportUser(reportParam))
            .thenReturn(Completable.error(exception))

        accountRepository.reportUser(reportParam).test()
            .assertError(exception)
    }
}