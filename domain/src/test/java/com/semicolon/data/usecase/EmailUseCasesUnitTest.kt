package com.semicolon.data.usecase

import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.service.EmailService
import com.semicolon.domain.usecase.email.CheckCertificationNumberUseCase
import com.semicolon.domain.usecase.email.SendCertificationMailUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EmailUseCasesUnitTest {

    @Mock
    private lateinit var emailService: EmailService

    private lateinit var checkCertificationNumberUseCase: CheckCertificationNumberUseCase
    private lateinit var sendCertificationMailUseCase: SendCertificationMailUseCase

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        checkCertificationNumberUseCase = CheckCertificationNumberUseCase(emailService)
        sendCertificationMailUseCase = SendCertificationMailUseCase(emailService)
    }

    @Test
    fun checkCertificationNumberTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )

        `when`(emailService.checkCertificationNumber(emailCertificationParam))
            .thenReturn(Single.just(Resource.success(Unit)))

        checkCertificationNumberUseCase.interact(emailCertificationParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun sendCertificationMailTest() {
        val email = "shw0471@naver.com"

        `when`(emailService.sendCertificationMail(email))
            .thenReturn(Single.just(Resource.success(Unit)))

        sendCertificationMailUseCase.interact(email).test()
            .assertValue(Resource.success(Unit))
    }
}