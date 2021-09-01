package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.Sex
import com.semicolon.domain.repository.AccountRepository
import com.semicolon.domain.service.AccountService
import com.semicolon.domain.service.AccountServiceImpl
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class AccountServiceUnitTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var accountService: AccountService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        accountService = AccountServiceImpl(accountRepository, errorHandler)
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

        `when`(accountRepository.registerAccount(registerAccountParam))
            .thenReturn(Completable.complete())

        accountService.registerAccount(registerAccountParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun registerAccountErrorTest() {
        val exception = Exception()
        val errorMessage = Error.CONFLICT
        val registerAccountParam = RegisterAccountParam(
            "shw0471@naver.com",
            "qwerty1234",
            "Shin",
            18,
            Sex.MALE,
            true,
            "yeah"
        )

        `when`(accountRepository.registerAccount(registerAccountParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        accountService.registerAccount(registerAccountParam).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun checkEmailDuplicationSuccessTest() {
        val email = "shw0471@naver.com"

        `when`(accountRepository.checkEmailDuplication(email))
            .thenReturn(Completable.complete())

        accountService.checkEmailDuplication(email).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun checkEmailDuplicationFailTest() {
        val exception = Exception()
        val errorMessage = Error.CONFLICT
        val email = "shw0471@naver.com"

        `when`(accountRepository.checkEmailDuplication(email))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        accountService.checkEmailDuplication(email).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun checkNicknameDuplicationSuccessTest() {
        val nickname = "Shin"

        `when`(accountRepository.checkNicknameDuplication(nickname))
            .thenReturn(Completable.complete())

        accountService.checkNicknameDuplication(nickname).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun checkNicknameDuplicationFailTest() {
        val exception = Exception()
        val errorMessage = Error.CONFLICT
        val nickname = "Shin"

        `when`(accountRepository.checkNicknameDuplication(nickname))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        accountService.checkNicknameDuplication(nickname).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun saveCoordinateSuccessTest() {
        val coordinateParam = CoordinateParam(1.2345, 1.2345)

        `when`(accountRepository.saveCoordinate(coordinateParam))
            .thenReturn(Completable.complete())

        accountService.saveCoordinate(coordinateParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun saveCoordinateFailTest() {
        val exception = Exception()
        val errorMessage = Error.CONFLICT
        val coordinateParam = CoordinateParam(1.2345, 1.2345)

        `when`(accountRepository.saveCoordinate(coordinateParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        accountService.saveCoordinate(coordinateParam).test()
            .assertValue(Resource.error(errorMessage))
    }
}