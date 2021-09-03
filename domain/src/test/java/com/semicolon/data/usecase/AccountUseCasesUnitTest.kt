package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.CoordinateParam
import com.semicolon.domain.param.RegisterAccountParam
import com.semicolon.domain.param.ReportParam
import com.semicolon.domain.param.Sex
import com.semicolon.domain.service.AccountService
import com.semicolon.domain.usecase.account.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AccountUseCasesUnitTest {

    @Mock
    private lateinit var accountService: AccountService

    private lateinit var checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase
    private lateinit var checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase
    private lateinit var registerAccountUseCase: RegisterAccountUseCase
    private lateinit var saveCoordinateUseCase: SaveCoordinateUseCase
    private lateinit var reportUserUseCase: ReportUserUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        checkEmailDuplicationUseCase = CheckEmailDuplicationUseCase(accountService)
        checkNicknameDuplicationUseCase = CheckNicknameDuplicationUseCase(accountService)
        registerAccountUseCase = RegisterAccountUseCase(accountService)
        saveCoordinateUseCase = SaveCoordinateUseCase(accountService)
        reportUserUseCase = ReportUserUseCase(accountService)
    }

    @Test
    fun checkEmailDuplicationTest() {
        val email = "shw0471@naver.com"

        `when`(accountService.checkEmailDuplication(email))
            .thenReturn(Single.just(Resource.success(Unit)))

        checkEmailDuplicationUseCase.interact(email).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun checkNickNameDuplicationTest() {
        val nickname = "Shin"

        `when`(accountService.checkNicknameDuplication(nickname))
            .thenReturn(Single.just(Resource.success(Unit)))

        checkNicknameDuplicationUseCase.interact(nickname).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun registerAccountTest() {
        val registerAccountParam = RegisterAccountParam(
            "shw0471@naver.com",
            "qwerty1234",
            "Shin",
            18,
            Sex.MALE,
            true,
            "yeah"
        )

        `when`(accountService.registerAccount(registerAccountParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        registerAccountUseCase.interact(registerAccountParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun saveCoordinateTest() {
        val coordinateParam = CoordinateParam(1.2345, 1.2345)

        `when`(accountService.saveCoordinate(coordinateParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        saveCoordinateUseCase.interact(coordinateParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun reportUserTest() {
        val reportParam = ReportParam(1234, "He is bad guy")

        `when`(accountService.reportUser(reportParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        reportUserUseCase.interact(reportParam).test()
            .assertValue(Resource.success(Unit))
    }
}