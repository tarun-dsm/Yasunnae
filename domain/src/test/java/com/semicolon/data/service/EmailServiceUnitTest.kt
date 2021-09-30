package com.semicolon.data.service

import com.semicolon.domain.base.Error
import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.base.Resource
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.repository.EmailRepository
import com.semicolon.domain.service.EmailService
import com.semicolon.domain.service.EmailServiceImpl
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EmailServiceUnitTest {

    @Mock
    private lateinit var emailRepository: EmailRepository

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var emailService: EmailService

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        emailService = EmailServiceImpl(emailRepository, errorHandler)
    }

    @Test
    fun checkCertificationNumberSuccessTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )

        `when`(emailRepository.checkCertificationNumber(emailCertificationParam))
            .thenReturn(Completable.complete())

        emailService.checkCertificationNumber(emailCertificationParam).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun checkCertificationNumberErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NOT_FOUND
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )

        `when`(emailRepository.checkCertificationNumber(emailCertificationParam))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        emailService.checkCertificationNumber(emailCertificationParam).test()
            .assertValue(Resource.error(errorMessage))
    }

    @Test
    fun sendCertificationMailSuccessTest() {
        val email = "shw0471@naver.com"

        `when`(emailRepository.sendCertificationMail(email))
            .thenReturn(Completable.complete())

        emailService.sendCertificationMail(email).test()
            .assertValue(Resource.success(Unit))
    }

    @Test
    fun sendCertificationMailErrorTest() {
        val exception = Exception()
        val errorMessage = Error.NOT_FOUND
        val email = "shw0471@naver.com"

        `when`(emailRepository.sendCertificationMail(email))
            .thenReturn(Completable.error(exception))

        `when`(errorHandler.handle(exception))
            .thenReturn(errorMessage)

        emailService.sendCertificationMail(email).test()
            .assertValue(Resource.error(errorMessage))
    }
}