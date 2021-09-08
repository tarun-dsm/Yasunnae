package com.semicolon.data.repository

import com.semicolon.data.datasource.EmailDataSource
import com.semicolon.domain.param.EmailCertificationParam
import com.semicolon.domain.repository.EmailRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class EmailRepositoryUnitTest {

    @Mock
    private lateinit var emailDataSource: EmailDataSource

    private lateinit var emailRepository: EmailRepository

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        emailRepository = EmailRepositoryImpl(emailDataSource)
    }

    @Test
    fun checkCertificationNumberSuccessTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )

        `when`(emailDataSource.checkCertificationNumber(emailCertificationParam))
            .thenReturn(Completable.complete())

        emailRepository.checkCertificationNumber(emailCertificationParam).test()
            .assertComplete()
    }

    @Test
    fun checkCertificationNumberErrorTest() {
        val emailCertificationParam = EmailCertificationParam(
            "shw0471@naver.com",
            "1234"
        )
        val exception = Exception()

        `when`(emailDataSource.checkCertificationNumber(emailCertificationParam))
            .thenReturn(Completable.error(exception))

        emailRepository.checkCertificationNumber(emailCertificationParam).test()
            .assertError(exception)
    }

    @Test
    fun sendCertificationMailSuccessTest() {
        val email = "shw0471@naver.com"

        `when`(emailDataSource.sendCertificationEmail(email))
            .thenReturn(Completable.complete())

        emailRepository.sendCertificationMail(email).test()
            .assertComplete()
    }

    @Test
    fun sendCertificationErrorTest() {
        val email = "shw0471@naver.com"
        val exception = Exception()

        `when`(emailDataSource.sendCertificationEmail(email))
            .thenReturn(Completable.error(exception))

        emailRepository.sendCertificationMail(email).test()
            .assertError(exception)
    }
}